package com.cyanidex.subsistence.lib.feature.config;

import com.cyanidex.subsistence.lib.feature.Feature;
import com.cyanidex.subsistence.lib.feature.IFeature;
import com.google.common.base.Joiner;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.List;
import java.util.regex.Pattern;

public class FeatureConfigManager {

    @SuppressWarnings("unchecked")
    public static void injectConfig(Configuration config, Class<? extends IFeature> featureClass, Feature feature) {
        for (Field field : featureClass.getDeclaredFields()) {
            if (field.isAnnotationPresent(Feature.Config.class) && Modifier.isStatic(field.getModifiers()) && !Modifier.isFinal(field.getModifiers())) {
                Feature.Config configAnnot = field.getAnnotation(Feature.Config.class);
                ForgeTypeAdaptation.ITypeAdapter typeAdapter = ForgeTypeAdaptation.ADAPTERS.get(field.getType());

                String category = "feature." + feature.name();
                if (!Strings.isNullOrEmpty(configAnnot.category()))
                    category += "." + configAnnot.category();

                if (typeAdapter != null) {
                    try {
                        field.setAccessible(true);
                        Property property = typeAdapter.getProp(config, category, field, null, configAnnot.comment());
                        field.set(null, typeAdapter.getValue(property));
                    } catch (Exception e) {
//                        Foody.LOGGER.error("Failed to handle config value for {}. {}", field.getName(), e);
                        e.printStackTrace();
                    }
                } else if (field.getType().isEnum()) {
                    try {
                        field.setAccessible(true);
                        Enum defaultValue = (Enum) field.get(null);
                        Property property = config.get(category, field.getName(), defaultValue.name(), configAnnot.comment());
                        property.setValidationPattern(makePattern((Class<? extends Enum>) field.getType()));
                        field.set(null, Enum.valueOf((Class<? extends Enum>) field.getType(), property.getString()));
                    } catch (Exception e) {
//                        Foody.LOGGER.error("Failed to handle config value for {}. {}", field.getName(), e);
                        e.printStackTrace();
                    }
                } else {
//                    Foody.LOGGER.error("Failed to handle config value for {}. Unknown value type.", field.getName());
                }
            }
        }
    }

    private static Pattern makePattern(Class<? extends Enum> enumClass) {
        List<String> lst = Lists.newArrayList();
        for (Enum e : enumClass.getEnumConstants())
            lst.add(e.name());
        return Pattern.compile(Joiner.on("|").join(lst));
    }
}

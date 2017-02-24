package com.cyanidex.subsistence.lib.feature;

import com.cyanidex.subsistence.lib.feature.config.FeatureConfigManager;
import com.google.common.base.Strings;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.commons.lang3.tuple.Pair;

import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

public class FeatureHandler {

    private final Configuration configuration;

    public FeatureHandler(Configuration configuration) {
        this.configuration = configuration;
    }

    public void preInit(List<Pair<IFeature, Feature>> features, FMLPreInitializationEvent event) {
        Iterator<Pair<IFeature, Feature>> iterator = features.iterator();
        while (iterator.hasNext()) {
            Pair<IFeature, Feature> feature = iterator.next();

            Property prop = configuration.get("feature", feature.getRight().name(), feature.getRight().enabledByDefault());
            prop.setRequiresMcRestart(true);
            String comment = "Enable the " + feature.getRight().name() + " feature.";
            if (!Strings.isNullOrEmpty(feature.getRight().description()))
                comment += "\n" + feature.getRight().description();
            comment += "\n[default: " + feature.getRight().enabledByDefault() + "]";

            prop.setComment(comment);
            if (!prop.getBoolean())
                iterator.remove();
        }

        Collections.sort(features, new Comparator<Pair<IFeature, Feature>>() {
            @Override
            public int compare(Pair<IFeature, Feature> o1, Pair<IFeature, Feature> o2) {
                int priority1 = o1.getRight().priority();
                int priority2 = o2.getRight().priority();

                if (priority1 < priority2)
                    return -1;
                if (priority1 == priority2)
                    return 0;
                return 1;
            }
        });

        for (Pair<IFeature, Feature> feature : features) {
            if (feature.getRight().handleConfig())
                FeatureConfigManager.injectConfig(configuration, feature.getLeft().getClass(), feature.getRight());

            feature.getLeft().preInit(event);
        }
    }

    public void init(List<Pair<IFeature, Feature>> features, FMLInitializationEvent event) {
        for (Pair<IFeature, Feature> feature : features)
            feature.getLeft().init(event);
    }

    public void postInit(List<Pair<IFeature, Feature>> features, FMLPostInitializationEvent event) {
        for (Pair<IFeature, Feature> feature : features)
            feature.getLeft().postInit(event);
    }
}
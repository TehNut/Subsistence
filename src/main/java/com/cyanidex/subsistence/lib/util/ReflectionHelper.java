package com.cyanidex.subsistence.lib.util;

import com.cyanidex.subsistence.lib.feature.Feature;
import com.cyanidex.subsistence.lib.feature.IFeature;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import net.minecraftforge.fml.common.discovery.ASMDataTable;
import org.apache.commons.lang3.tuple.Pair;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ReflectionHelper {

    public static final Map<Class<?>, IAnnotationPreloader> PRELOADERS = Maps.newHashMap();

    static {
        PRELOADERS.put(IFeature.class, Feature.PRELOADER);
    }

    /**
     * Populates a list with <b>all</b> fields (including inherited fields) of the given class.
     *
     * @param fields - List of fields to populate
     * @param type - The class to get the fields of
     */
    public static void getAllFields(List<Field> fields, Class<?> type) {
        fields.addAll(Arrays.asList(type.getDeclaredFields()));
        if (type.getSuperclass() != null)
            getAllFields(fields, type.getSuperclass());
    }

    public static <T, A extends Annotation> List<Pair<T, A>> getAnnotationInstances(ASMDataTable dataTable, Class<A> annotationClass, Class<T> typeClass) {
        List<Pair<T, A>> discoveredAnnotations = Lists.newArrayList();
        Set<ASMDataTable.ASMData> discoveredPlugins = dataTable.getAll(annotationClass.getCanonicalName());

        for (ASMDataTable.ASMData data : discoveredPlugins) {
            if (PRELOADERS.containsKey(typeClass)) {
                IAnnotationPreloader preloader = PRELOADERS.get(typeClass);
                if (!preloader.shouldLoad(data.getAnnotationInfo()))
                    continue;
            }

            try {
                Class<?> asmClass = Class.forName(data.getClassName());
                Class<? extends T> pluginClass = asmClass.asSubclass(typeClass);

                discoveredAnnotations.add(Pair.of(pluginClass.newInstance(), pluginClass.getAnnotation(annotationClass)));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return discoveredAnnotations;
    }

    public interface IAnnotationPreloader {

        /**
         * Called just before {@link #getAnnotationInstances(ASMDataTable, Class, Class)} attempts to load the discovered class.
         *
         * @param annotationData - A map of field name -> field value from the annotation
         * @return whether this class should be loaded.
         */
        boolean shouldLoad(Map<String, Object> annotationData);
    }
}

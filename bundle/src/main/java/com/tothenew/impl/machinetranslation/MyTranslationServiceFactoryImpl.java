package com.tothenew.impl.machinetranslation;

import com.adobe.granite.translation.api.*;
import com.adobe.granite.translation.core.common.AbstractTranslationServiceFactory;
import org.apache.commons.lang.StringUtils;
import org.apache.felix.scr.annotations.*;
import org.apache.sling.api.resource.*;
import org.apache.sling.commons.osgi.PropertiesUtil;
import org.osgi.service.component.ComponentContext;

import java.util.Dictionary;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

@Service
@Component(label="Example Translation Connector Factory", metatype=true, immediate=true)
@Properties(value = {
        @Property(name = "service.description", value = "My translation service"),
        @Property(name = TranslationServiceFactory.PROPERTY_TRANSLATION_FACTORY,
                value = "myTranslator", label="My Translator Factory Name",
                description = "The Unique ID associated with this Translation Factory Connector")
})
public class MyTranslationServiceFactoryImpl extends AbstractTranslationServiceFactory implements TranslationServiceFactory {

        @Reference
        ResourceResolverFactory resourceResolverFactory;
        @Reference
        TranslationConfig translationConfig;

        /* Language mapping node locations */
        @Property(label="Example Connector Language Map", description="The location of the language mapping nodes for" +
                " the example connector", value = "/libs/granite/translation/connector/config/msft/languageMapping")
        private static final String PROPERTY_LANGUAGE_MAP = "languageMapLocation";
        private String languageMapLocation;
        private String languageMapProp = "languageMapping";

        /* Category mapping node locations */
        @Property(label="Example Connector Category Map", description="The location of the category mapping nodes for" +
                " the example connector", value = "/libs/granite/translation/connector/config/msft/categoryMapping")
        private static final String PROPERTY_CATEGORY_MAP = "categoryMapLocation";
        private String categoryMapLocation;
        private String categoryMapProp="categoryMapping";

        /* Translation attribute */
        @Property(value="This translation by Example Service",
                label="Translation Service Attribution",
                description ="The attribution for translated UGC content")
        static  String TRANSLATION_ATTRIBUTE = "translationServiceAttribution";

        /* Service label */
        @Property(value="Example Translation Service",
                label="Translation Service Label",
                description="The label that appears in the cloud service configuration")
        static final String SERVICE_LABEL = "Custom Translator";

    //    private static final Logger log = LoggerFactory.getLogger(MyTranslationServiceFactoryImpl.class);

        protected void activate(ComponentContext componentContext) {
            Dictionary<?, ?> properties = componentContext.getProperties();
            /* Required for the Translation Integration cloud service configuration */
            factoryName = PropertiesUtil.toString(properties.get(TranslationServiceFactory.PROPERTY_TRANSLATION_FACTORY), StringUtils.EMPTY);
            status = TranslationConstants.ServiceStatus.UNKNOWN_STATUS;
            /* Obtain the paths of the language and category mapping nodes */
            languageMapLocation = PropertiesUtil.toString(properties.get(PROPERTY_LANGUAGE_MAP), StringUtils.EMPTY);
            categoryMapLocation = PropertiesUtil.toString(properties.get(PROPERTY_CATEGORY_MAP), StringUtils.EMPTY);
        }

        public TranslationService createTranslationService(Resource resource) throws TranslationException {
            Map<String, String> languageMap = getPropertyMap(languageMapLocation, languageMapProp);
            Map<String, String> categoryMap = getPropertyMap(categoryMapLocation, categoryMapProp);
            return new MyTranslationServiceImpl(resource, languageMap,categoryMap, factoryName, SERVICE_LABEL, TRANSLATION_ATTRIBUTE, translationConfig);
        }
        /*
         * Reads language or cateogry mapping nodes and adds them to a Map object.
         * @param mapLocation A String that represents the path of the parent node of the mapping nodes.
         * @param strPropertyMapLabel A String that represents the name of the node property that contains the mapped language or cateogry node.
         * @returns A Map with items that have the key of the AEM langauge or category code and a value of the code for your translation provider.
         */
        private Map<String, String> getPropertyMap(String mapLocation, String strPropertyMapLabel) throws TranslationException {
            HashMap<String, String> connectorResourceMap = new HashMap<String, String>();
            ResourceResolver resolver = null;
            try{
                resolver = resourceResolverFactory.getAdministrativeResourceResolver(null);
                Resource resourceParent = resolver.getResource(mapLocation);
                Iterator<Resource> resourceIterator = resourceParent.listChildren();
                while(resourceIterator.hasNext()){
                    Resource keyName = resourceIterator.next();
                    ValueMap resourceProps = keyName.adaptTo(ValueMap.class);
                    connectorResourceMap.put(keyName.getName(),resourceProps.get(strPropertyMapLabel, String.class));
                }
            } catch(LoginException le){
                throw new TranslationException("Error obtaining resource resolver", le,
                        TranslationException.ErrorCode.GENERAL_EXCEPTION);
            } finally{
                if(resolver != null){
                    resolver.close();
                }
            }
            return connectorResourceMap;
        }
}
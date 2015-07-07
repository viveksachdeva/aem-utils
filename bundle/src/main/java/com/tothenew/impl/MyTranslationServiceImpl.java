package com.tothenew.impl;

import com.adobe.granite.translation.api.TranslationConstants;
import com.adobe.granite.translation.api.TranslationException;
import com.adobe.granite.translation.api.TranslationResult;
import com.adobe.granite.translation.api.TranslationService;
import com.adobe.granite.translation.api.TranslationConfig;
import com.adobe.granite.translation.core.common.AbstractTranslationService;


import com.tothenew.model.MyTranslationResult;
import org.apache.sling.api.resource.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;


public class MyTranslationServiceImpl extends AbstractTranslationService implements TranslationService {

    private static final Logger log = LoggerFactory.getLogger(MyTranslationServiceImpl.class);

    //Constructor
    public  MyTranslationServiceImpl(Resource resource,
                                     Map<String, String> availableLanguageMap,
                                     Map<String, String> availableCategoryMap, String name,
                                     String label, String attribution, TranslationConfig tc){
        super(resource, availableLanguageMap, availableCategoryMap, name, label, attribution, tc);
        log.info("**** Starting Constructor for: MyTranslationServiceImpl");
    }
    public Map<String, String> supportedLanguages() {
        log.debug("In Function: supportedLanguages");
        if (availableLanguageMap.size() <= 0){
            return Collections.unmodifiableMap(new HashMap<String, String>());
        }
        return Collections.unmodifiableMap(availableLanguageMap);
    }
    public boolean isDirectionSupported(String sourceLanguage, String targetLanguage) throws TranslationException {
        log.debug("In Function: isSupportedDirection");
        return true;
    }
    public String detectLanguage(String toDetectSource, TranslationConstants.ContentType contentType) throws TranslationException {
        log.debug("In Function: detectLanguage");

        log.info("Detecting Language::::"+toDetectSource);
       return "en";
    }
    public TranslationResult translateString(String sourceString, String sourceLanguage, String targetLanguage, TranslationConstants.ContentType contentType,
                                             String contentCategory) throws TranslationException {
        log.info("Translate String::::{}:::{}:::{}:::{}:::{}",sourceString,sourceLanguage,targetLanguage,contentType,contentCategory);
        return new MyTranslationResult("changed",sourceString);
//        throw new TranslationException("This function is not implemented", TranslationException.ErrorCode
//                .SERVICE_NOT_IMPLEMENTED);
    }
    public TranslationResult[] translateArray(String[] sourceStringArr, String sourceLanguage,
                                              String targetLanguage,
                                              TranslationConstants.ContentType contentType,
                                              String contentCategory) throws TranslationException {
        log.info("Translated Array:::{}:::{}:::{}:::{}:::{}:::{}",sourceStringArr,sourceLanguage,targetLanguage,contentCategory);
        throw new TranslationException("This function is not implemented", TranslationException.ErrorCode
                .SERVICE_NOT_IMPLEMENTED);
    }
    public TranslationResult[] getAllStoredTranslations(String sourceString, String sourceLanguage, String targetLanguage,
                                                        TranslationConstants.ContentType contentType, String contentCategory,
                                                        String userId, int maxTranslations)
            throws TranslationException {

        log.info("Get All Stored Translations::::{}:::{}::::{}:::{}::{}",sourceString,sourceLanguage,targetLanguage,contentCategory,userId);
        throw new TranslationException("This function is not implemented", TranslationException.ErrorCode
                .SERVICE_NOT_IMPLEMENTED);
    }
    public void storeTranslation(String[] originalText, String sourceLanguage, String targetLanguage,
                                 String[] updatedTranslation, TranslationConstants.ContentType contentType,
                                 String contentCategory, String userId, int rating, String path)
            throws TranslationException {

        log.info("Store Translations:::::{}::{}::{}::{}:::{}::{}::{}:::{}", originalText, sourceLanguage,targetLanguage,updatedTranslation,contentCategory,userId,path,rating);
        throw new TranslationException("This function is not implemented", TranslationException.ErrorCode
                .SERVICE_NOT_IMPLEMENTED);
    }
    public void storeTranslation(String originalText, String sourceLanguage, String targetLanguage,
                                 String updatedTranslation, TranslationConstants.ContentType contentType,
                                 String contentCategory, String userId, int rating, String path)
            throws TranslationException {
        log.debug("Starting function:  updateTranslation");

        throw new TranslationException("This function is not implemented", TranslationException.ErrorCode
                .SERVICE_NOT_IMPLEMENTED);
    }
}
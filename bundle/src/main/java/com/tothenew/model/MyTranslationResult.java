package com.tothenew.model;

import com.adobe.granite.translation.api.TranslationConstants;
import com.adobe.granite.translation.api.TranslationResult;

public class MyTranslationResult implements TranslationResult {

    String myTranslatedString ;
    String mySourceString ;

    public MyTranslationResult(String test, String source){
           myTranslatedString = test;
        mySourceString = source;
    }

    @Override
    public String getTranslation() {
        return myTranslatedString;
    }

    @Override
    public String getSourceLanguage() {
        return "en";  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public String getTargetLanguage() {
        return "fr";  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public TranslationConstants.ContentType getContentType() {
        return TranslationConstants.ContentType.HTML;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public String getCategory() {
        return "general";  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public String getSourceString() {
        return mySourceString;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public int getRating() {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public String getUserId() {
        return "admin";  //To change body of implemented methods use File | Settings | File Templates.
    }
}

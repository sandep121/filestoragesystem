package com.sandeep.filestorage.util;

import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.stereotype.Service;

import java.util.Locale;
@Service
public class Messages implements MessageSourceAware {

    private MessageSource messageSource;
    public String getMessage(String tag) {
        return getMessage(tag, new Object[0]);
    }

    public String getMessage(String tag, Object... params) {
        return messageSource.getMessage(tag, params, Locale.US);
    }

    @Override
    public void setMessageSource(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

}

/*
*
* */

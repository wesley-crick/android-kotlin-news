package com.wesley.crick.kotlinnews.callbacks;

import com.wesley.crick.kotlinnews.objects.ResponseTemplate;

public interface SimpleCallback<T> {
    void call(ResponseTemplate<T> rt);
}


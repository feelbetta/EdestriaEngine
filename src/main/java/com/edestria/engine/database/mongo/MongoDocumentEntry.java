package com.edestria.engine.database.mongo;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor @Getter
public class MongoDocumentEntry<String, Object> {

    private String key;
    private Object value;
}

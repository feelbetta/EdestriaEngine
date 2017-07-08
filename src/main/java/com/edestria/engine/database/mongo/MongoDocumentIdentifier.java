package com.edestria.engine.database.mongo;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor @Getter
public class MongoDocumentIdentifier<String, Object> {

    private String identifier;
    private Object identifierValue;
}

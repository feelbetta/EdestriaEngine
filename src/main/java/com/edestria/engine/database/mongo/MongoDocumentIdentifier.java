package com.edestria.engine.database.mongo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor @Getter @Setter
public class MongoDocumentIdentifier<String, Object> {

    private String identifier;
    private Object identifierValue;
}

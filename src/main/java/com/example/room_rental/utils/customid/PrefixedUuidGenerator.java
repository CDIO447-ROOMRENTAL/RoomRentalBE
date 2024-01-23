package com.example.room_rental.utils.customid;

import org.hibernate.MappingException;
import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.UUIDGenerator;
import org.hibernate.type.Type;
import org.hibernate.service.ServiceRegistry;

import java.io.Serializable;
import java.util.Properties;

public class PrefixedUuidGenerator extends UUIDGenerator {

    private static String prefix;

    public static String getPrefix() {
        return prefix;
    }

    public static void setPrefixFromParameters(String prefixValue) {
        prefix = prefixValue;
    }

    @Override
    public Serializable generate(SharedSessionContractImplementor session, Object obj) {
        if (prefix != null) {
            // Manually prepend the prefix to the generated UUID
            return prefix + super.generate(session, obj);
        }

        return super.generate(session, obj);
    }

    @Override
    public void configure(Type type, Properties params, ServiceRegistry serviceRegistry) throws MappingException {
        super.configure(type, params, serviceRegistry);
        String prefixValue = params.getProperty("prefix");
        if (prefixValue != null) {
            setPrefixFromParameters(prefixValue);
        }
    }
}

package utils;

import lombok.Getter;
import java.io.Serial;

public class DealershipExceptions {

    public static class DealershipException extends RuntimeException {
        @Serial
        private static final long serialVersionUID = 1L;

        public DealershipException(String message) {
            super(message);
        }

        public DealershipException(String message, Throwable cause) {
            super(message, cause);
        }
    }

    public static class ValidationException extends DealershipException {
        @Serial
        private static final long serialVersionUID = 1L;

        public ValidationException(String message) {
            super(message);
        }
    }

    public static class DatabaseException extends DealershipException {
        @Serial
        private static final long serialVersionUID = 1L;

        public DatabaseException(String message, Throwable cause) {
            super(message, cause);
        }
    }

    @Getter
    public static class DuplicateKeyException extends DealershipException {
        @Serial
        private static final long serialVersionUID = 1L;
        private final String field;
        private final String value;

        public DuplicateKeyException(String field, String value, String message) {
            super(message);
            this.field = field;
            this.value = value;
        }
    }

    @Getter
    public static class EntityNotFoundException extends DealershipException {
        private final String entityType;
        private final String fieldName;
        private final Object fieldValue;

        public EntityNotFoundException(String entityType, String fieldName, Object fieldValue) {
            super(String.format("No se encontró %s con %s: %s", entityType, fieldName, fieldValue));
            this.entityType = entityType;
            this.fieldName = fieldName;
            this.fieldValue = fieldValue;
        }
    }
}
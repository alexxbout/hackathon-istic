package fr.istic.app.common.domain.exception;

import fr.istic.app.common.domain.exception.enums.ExceptionMessages;

public class SearchParamsAllNull extends RuntimeException {
    public SearchParamsAllNull() {
        super(ExceptionMessages.SEARCH_CRITERIA_ALL_NULL.getMessage());
    }
}

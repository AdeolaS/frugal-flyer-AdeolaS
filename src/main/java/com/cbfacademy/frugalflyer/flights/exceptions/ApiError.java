package com.cbfacademy.frugalflyer.flights.exceptions;

import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Record to be returned in place of just a string when exceptions occur.
 */

// Record, so the members, contructors and getters are automatically generated.
// Makes it final (immutable) too
// Should help make the Swagger Documentation Responses prettier 

@Schema(description = "Record to be returned in place of just a string when exceptions occur.")
public record ApiError(
    @Schema(name = "message", example = "Error message will be written here")
    String message,
    @Schema(name = "status", example = "123") 
    int status,
    LocalDateTime timeOfError
    ) {}

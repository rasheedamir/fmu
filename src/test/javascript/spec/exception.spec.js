'use strict';

describe('Exception handling', function() {
    var exceptionHandlerProvider, rootscope;
    beforeEach(function() {
        module('util.exception', function($provide, _fmuExceptionHandlerProvider_) {
            exceptionHandlerProvider = _fmuExceptionHandlerProvider_;            
            mockLogger($provide);
        });

        inject(function(_$rootScope_) {
        	rootscope = _$rootScope_;
        });
    });

    describe("Default configuration", function() {
        var ehp;
        beforeEach(function() {
            inject(function(fmuExceptionHandler) {
                ehp = fmuExceptionHandler;
            });
        });

        it("should define an exception handler", function() {
            expect(ehp).toBeDefined();
            expect(ehp.config.appErrorPrefix).toBe(undefined);
        });
    });

    describe("Configured configuration", function() {
        var prefix = '[My prefix] : ';
        var handler;
        var errMsg = 'an error message';

        var fnThatThrow = function() {
            throw new Error(errMsg);
        };


        beforeEach(function() {
        	exceptionHandlerProvider.configure(prefix);
            inject(function(fmuExceptionHandler) {
                handler = fmuExceptionHandler;
            });
        });

        

        it("should catch the error when thrown", function() {
            try {
                rootscope.$apply(fnThatThrow);
            } catch (ex) {
                expect(ex.message).toEqual(prefix + errMsg);
            }
        });
    });
});
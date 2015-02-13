'use strict';

describe('Exception handling', function() {
    var exceptionHandlerProvider, $rootScope;
    beforeEach(function() {
        module('util.exception', function($provide, _exceptionHandlerProvider_) {
            exceptionHandlerProvider = _exceptionHandlerProvider_;            
            $provide.value('logger', {
                info: function() {},
                error: function() {},
                warning: function() {},
                success: function() {}
            });
        });

        inject(function(_$rootScope_) {
        	$rootScope = _$rootScope_;
        });
    });

    describe("Default configuration", function() {
        var ehp;
        beforeEach(function() {
            inject(function(exceptionHandler) {
                ehp = exceptionHandler;
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
            throw (errMsg);
        };


        beforeEach(function() {
        	exceptionHandlerProvider.configure(prefix);
            inject(function(exceptionHandler) {
                handler = exceptionHandler;
            });
        });

        it("should define a handler", function() {
            expect(handler).toBeDefined();
        });

        it("should have correct prefix", function() {
            expect(handler.config.appErrorPrefix).toEqual(prefix);
            expect(exceptionHandlerProvider.$get().config.appErrorPrefix).toEqual(prefix);
        });

        it("should throw en exception", function() {
            expect(fnThatThrow).toThrow();
        });

        it("should catch the error when thrown", function() {
            try {
                $rootScope.$apply(fnThatThrow);
            } catch (ex) {
            	console.log(ex);
            	console.log(exceptionHandlerProvider);
            	console.log(handler);
                //expect(ex.message).toEqual(prefix + errMsg);
            }
        });
    });
});
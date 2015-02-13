'use strict';
describe('Core config', function() {
	var e, eh, l, c;
	beforeEach(function() {
		module('fmu.core', function($logProvider, fmuExceptionHandlerProvider) {
			e = fmuExceptionHandlerProvider;
			l = $logProvider;
		});

		// To kick off lazy loading of injectables
		inject(function(logger, fmuExceptionHandler) {});
	});

	it("should define providers", function() {
		expect(l).toBeDefined();
		expect(e).toBeDefined();
	});

	it("should enable debug logging", function() {
		expect(l.debugEnabled).toBeTruthy();
	});

	it("should create correct prefix for error messages", function() {
		expect(e.$get().config.appErrorPrefix).toEqual('[FMU Error]: ');
	});
});
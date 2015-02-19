'use strict';
describe('Core config', function() {
	var e, eh, l, c, r, rh;
	beforeEach(function() {
		module('fmu.core', function($logProvider, fmuExceptionHandlerProvider, routehelperConfigProvider) {
			e = fmuExceptionHandlerProvider;
			l = $logProvider;
			r = routehelperConfigProvider;
		});

		// To kick off lazy loading of injectables
		inject(function(logger, fmuExceptionHandler, routeHelper) {
			rh = routeHelper; 
		});
	});

	it("should define providers", function() {
		expect(l).toBeDefined();
		expect(e).toBeDefined();
		expect(r).toBeDefined();
	});

	it("should enable debug logging", function() {
		expect(l.debugEnabled).toBeTruthy();
	});

	it("should create correct prefix for error messages", function() {
		expect(e.$get().config.appErrorPrefix).toEqual('[FMU Error]: ');
	});

	it("should define routeHelper", function() {
		expect(rh).toBeDefined();
	});

	it("should set default docTitle", function() {
		expect(r.config.docTitle).toEqual('fmu-route-title/Fmu');
	});
});
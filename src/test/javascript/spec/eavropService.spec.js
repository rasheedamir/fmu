'use strict';
describe("Eavrop Service", function() {
	var eavropService;
	beforeEach(function() {
		module('fmu');
		inject(function(_eavropService_){
			eavropService = _eavropService_;
		})
	});

	it("Should define eavropService", function() {
		expect(eavropService).toBeDefined();
	});
});
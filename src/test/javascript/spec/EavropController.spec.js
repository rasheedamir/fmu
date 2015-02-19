'use strict'

describe("Eavrop controller", function() {
	var controller, eavropCtrl, scope;
	beforeEach(function() {
		module('fmu');
		inject(function(_$controller_, $rootScope) {
			controller = _$controller_;
			scope = $rootScope.$new();
			eavropCtrl = controller('EavropController', {
				'$scope': scope,
				 'currentEavrop': {}, 
				 'AuthService': {}, 
				 'patientInfo': {},
				 'eavropService': {}
			});
		});
	});

	it("should define EavropController", function() {
		expect(controller).toBeDefined();
		expect(eavropCtrl).toBeDefined();
	});
});
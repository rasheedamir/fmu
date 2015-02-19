'use strict'

describe("Eavrop controller", function() {
	var controller, eavropCtrl, scope;
	beforeEach(function() {
		module('fmu');
		inject(function(_$controller_, $rootScope, $filter, AuthService, investigationService, logger) {
			controller = _$controller_;
			scope = $rootScope.$new();
			eavropCtrl = controller('InvestigationController',{
				$scope: scope,
				$filter: $filter, 
				$stateParams: {}, 
				AuthService: AuthService, 
				InvestigationService: investigationService, 
				$modal: {}, 
				currentEavrop: {}, 
				logger: logger
			});
		});
	});

	it("should define EavropController", function() {
		expect(controller).toBeDefined();
		expect(eavropCtrl).toBeDefined();
	});
});
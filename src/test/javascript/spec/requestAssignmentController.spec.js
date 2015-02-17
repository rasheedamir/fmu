'use strict'


// TODO Must figure out how to test modals nested controllers
describe("Eavrop controller", function() {
	var controller, assignCtrl, scope;
	var paramsMock = {
		eavropId: 'testid'
	};

	var mockDataService = {

	};

	beforeEach(function() {
		module('fmu');
		inject(function(_$controller_, $rootScope, $modal) {
			controller = _$controller_;
			scope = $rootScope.$new();
			assignCtrl = controller('RequestAssignmentController', {
				$scope: scope, 
				$modal: $modal, 
				$stateParams: {}, 
				Dataservice: mockDataService,
			});
		});
	});

	it("should define EavropController", function() {
		expect(controller).toBeDefined();
		expect(assignCtrl).toBeDefined();
	});
});
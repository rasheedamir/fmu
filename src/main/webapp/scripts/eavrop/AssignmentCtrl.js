'use strict';

angular.module('fmuClientApp').controller('AssignmentCtrl', ['$scope', '$modal', '$stateParams', 'EavropVardgivarenheter', 'EavropAssignment', '$state',function($scope, $modal, $stateParams, EavropVardgivarenheter, EavropAssignment, $state){
	
	$scope.openAssignUtredareDialog = function(){
        var assignModal = $modal.open({
            templateUrl: 'views/eavrop/assign-utredare-modal.html',
            size: 'md',
            resolve: {
            	vardgivarenheter: function(){return EavropVardgivarenheter.query({eavropId: $stateParams.eavropId})},
            },
            controller: function ($scope, vardgivarenheter, $modalInstance, EavropAssignment, $stateParams, $state) {
            	$scope.vardgivarenheter = vardgivarenheter;
            	$scope.model = {selectedVardgivarenhet: {}}
            	
            	$scope.close = function(){
            		$modalInstance.dismiss('cancel');
            	}
            	
            	$scope.assign = function(){
            		var res = new EavropAssignment({eavropId: $stateParams.eavropId});
            		console.log($scope.model.selectedVardgivarenhet);
            		var result = res.$assign({veId: $scope.model.selectedVardgivarenhet.id});
            		result.then(function(res){
            			$state.transitionTo($state.current, $stateParams, {
            			    reload: true,
            			    inherit: false,
            			    notify: true
            			});
            			
            			$modalInstance.close();
            		});
            	}
            }
        });
	}
}]);
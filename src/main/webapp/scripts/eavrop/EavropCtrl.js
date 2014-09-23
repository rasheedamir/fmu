'use strict';

angular.module('fmuClientApp').
    controller('EavropCtrl',['$scope','$modal', 'currentEavrop', function($scope, $modal, currentEavrop){

    $scope.currentEavrop = currentEavrop;

    $scope.openAddDocumentModal = function(){
        var mod = $modal.open({
            templateUrl: 'views/eavrop/add-doc-modal.html',
            controller: 'AddDocCtrl',
            size: 'sm'
        });
    };
}]);

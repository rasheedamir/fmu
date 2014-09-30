'use strict';
angular.module('fmuClientApp')
.controller('AddDocCtrl', ['$scope', '$modalInstance','currentEavrop', function($scope, $modalInstance, currentEavrop) {

    $scope.currentEavrop = currentEavrop;
    $scope.picker = {opened: false};
    $scope.doc = {regDate: new Date()};

    $scope.open = function($event){
        $event.preventDefault();
        $event.stopPropagation();
        $scope.picker.opened = true;
    };

    $scope.save = function(){
        $scope.currentEavrop.documents.push($scope.doc);
        $scope.currentEavrop.$update();
        $modalInstance.close();
    };
    $scope.close = function(){
        $modalInstance.dismiss('cancel');
    };
}]);

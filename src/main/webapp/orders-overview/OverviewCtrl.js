'use strict';

angular.module('fmuClientApp')
.controller('OverviewCtrl', ['$scope', function($scope){
    $scope.select = function(option) {
        $scope.$state.go(option.state);
    };
}]);

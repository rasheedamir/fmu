'use strict';

angular.module('fmuClientApp')
.controller('OverviewCtrl', ['$scope', function($scope){
    $scope.overviews = [
        {name: 'Beställningar', state: 'overview.orders'},
        {name: 'Pågående utredningar', state: 'overview.ongoing'},
        {name: 'Genomförda utredningar', state: 'overview.completed'}
    ];

    $scope.currentOverview = $scope.overviews[0];

    $scope.$watch('currentOverview', function(val){
        $scope.$state.go(val.state);
    });
}]);

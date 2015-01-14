'use strict';

angular.module('fmuClientApp')
.controller('OverviewCtrl', ['$scope', function($scope){
    $scope.overviews = [
        {name: 'Beställningar', state: 'overview.orders'},
        {name: 'Pågående utredningar', state: 'overview.ongoing'},
        {name: 'Genomförda utredningar', state: 'overview.completed'}
    ];

    for (var i=0; i < $scope.overviews.length; ++i) {
        if($scope.overviews[i].state == $scope.$state.current.name){
            $scope.currentOverview = $scope.overviews[i];
            break;
        }
    }

    $scope.select = function(option) {
        $scope.$state.go(option.state);
    };
}]);

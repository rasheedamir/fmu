'use strict';

angular.module('fmuClientApp').
    controller('EavropCtrl',['$scope', 'currentEavrop', function($scope, currentEavrop){

    $scope.currentEavrop = currentEavrop;

    $scope.links = [
        {name: 'Beställning', state: 'order'},
        {name: 'Utredning', state: 'investigation'},
        {name: 'Alla händelser', state: 'allevents'},
        {name: 'Anteckningar', state: 'notes'},
        {name: 'Underlag för ersättning', state: 'compensation'},
    ];

    $scope.clickLink = function(link){
        var url = '^.'+link.state;
        $scope.$state.go(url);
    };
}]);

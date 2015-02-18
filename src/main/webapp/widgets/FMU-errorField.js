'use strict';
angular.module('fmu.widgets')
    .directive('fmuErrorField', function(){
        return {
            restrict: 'E',
            scope: {
                errors: '=',
                title: '@'
            },
            template: '<div class="fmu-error-field flipInX fast" ng-if="errors && errors.length > 0">' +
                            '<label>Följande fel uppstod: </label>' +
                            '<p ng-repeat="error in errors"><i>{{error}}</i></p>' +
                       '</div>'
        };
    });

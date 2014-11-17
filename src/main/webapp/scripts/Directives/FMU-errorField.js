'use strict';
angular.module('fmuClientApp')
    .directive('fmuErrorField', function(AuthService){
        return {
            restrict: 'E',
            scope: {
                errors: '=',
                title: '@'
            },
            controller: function ($scope, $element) {

            },
            template: '<div class="row fmu-error-field" ng-if="errors && errors.length > 0">' +
                            '<label>Följande fel uppstod: </label>' +
                            '<p ng-repeat="error in errors"><i>{{error}}</i></p>' +
                       '</div>'
        };
    });

'use strict';
angular.module('fmu.widgets')
.directive('fmuHandlingarTable', ['gettext', function(gettext){
    return {
        scope: {
            handlingar: '=handlingar'
        },
        restrict: 'E',
        link: function(scope){
            scope.text = {
                handling: gettext('handlingar-tabell/Handling'),
                registeredBy: gettext('handlingar-tabell/Registrerad av'),
                registeredDate: gettext('handlingar-tabell/Registrerad, datum')
            };
        }, 
        template:'<table class="fmu-table"> ' +
            '<thead> ' +
                '<tr> ' +
                    '<th>{{text.handling | translate}}</th> ' +
                    '<th>{{text.registeredBy | translate}}</th> ' +
                    '<th>{{text.registeredDate | translate}}</th> ' +
                    '</tr> ' +
            '</thead> ' +
            '<tbody> ' +
                '<tr ng-if="!handlingar || handlingar.length == 0">' +
                    '<td ng-repeat="i in [1, 2, 3]">&nbsp;</td>' +
                '</tr>'+

                '<tr ng-repeat="doc in handlingar"> ' +
                    '<td>{{doc.name}}</td> ' +
                    '<td>{{doc.regBy}}</td> ' +
                    '<td>{{doc.regDate | date:\'yyyy-MM-dd\'}}</td> ' +
                    '</tr> ' +
            '</tbody> ' +
        '</table> '
    };
}])
.directive('fmuTillaggTable', ['gettext', function(gettext){
    return {
        scope: {
            tillagg: '=tillagg'
        },
        restrict: 'E',
        link: function(scope){
            scope.text = {
                handling: gettext('Tillägg-tabell/Handling'),
                sentBy: gettext('Tillägg-tabell/Begäran skickad, av'),
                sentDate: gettext('Tillägg-tabell/Begäran skickad, datum'),
                comment: gettext('Tillägg-tabell/Kommentar'),
                sentTo: gettext('Tillägg-tabell/Begäran skickad till')
            };
        },
        template:
            '<table class="fmu-table"> ' +
                '<thead> ' +
                    '<tr> ' +
                        '<th>{{text.handling | translate}}</th> ' +
                        '<th>{{text.sentBy | translate}}</th> ' +
                        '<th>{{text.sentDate | translate}}</th> ' +
                        '<th>{{text.comment | translate}}</th> ' +
                        '<th>{{text.sentTo | translate}}</th> ' +
                    '</tr> ' +
                '</thead> ' +
                '<tbody> ' +
                    '<tr ng-if="!tillagg || tillagg.length == 0">' +
                        '<td ng-repeat="i in [1, 2, 3, 4, 5]">&nbsp;</td>' +
                    '</tr>'+

                    '<tr ng-repeat="am in tillagg"> ' +
                        '<td>{{am.name}}</td> ' +
                        '<td>{{am.sentBy}}</td> ' +
                        '<td>{{am.sentDate | date: \'yyyy-MM-dd\'}}</td> ' +
                        '<td>{{am.comment}}</td> ' +
                        '<td>{{am.sentTo}}</td> ' +
                    '</tr> ' +
                '</tbody> ' +
            '</table>'
    };
}])
.directive('fmuAnteckningarTable', ['$filter', 'gettext', function($filter, gettext){
    return {
        scope: {
            anteckningar: '=',
            rowRemovable: '=?',
            removeFunc: '&'
        },
        restrict: 'E',
        controller: ['$scope', function ($scope) {
            $scope.toYYMMDD = function (date) {
                return $filter('date')(date, 'yyyy-MM-dd');
            };
        }],

        link: function (scope) {
            scope.remove = function (note) {
                return scope.rowRemovable && scope.removeFunc() ? scope.removeFunc()(note) : null;
            };

            scope.text = {
                content: gettext('Anteckningar-tabell/Innehåll'),
                createdBy: gettext('Anteckningar-tabell/Skapad av'),
                date: gettext('Anteckningar-tabell/Datum'),
                remove: gettext('Anteckningar-tabell/Tabort')
            };
        },
        template:
            '<table class="fmu-table greyed-hover"> ' +
            '<thead> ' +
                '<tr> ' +
                        '<th>{{text.content | translate}}</th> ' +
                        '<th>{{text.createdBy | translate}}</th> ' +
                        '<th>{{text.date | translate}}</th> ' +
                        '<th ng-if="rowRemovable">{{text.remove | translate}}</th> ' +
                '</tr> ' +
            '</thead> ' +
            '<tbody> ' +
                '<tr class="blank-row" ng-if="!anteckningar || anteckningar.length == 0">' +
                    '<td ng-repeat="i in [1, 2, 3]">&nbsp;</td>' +
                    '<td ng-if="rowRemovable">&nbsp;</td>' +
                '</tr>'+
                '<tr ng-repeat="note in anteckningar"> ' +
                    '<td class="text-capitalize">{{note.contents}}</td> ' +
                    '<td class="text-capitalize">{{note.createdBy}}</td> ' +
                    '<td>{{toYYMMDD(note.date)}}</td> ' +
                    '<td align="middle" ng-if="rowRemovable"> ' +
                        '<span ng-if="note.removable" class="btn btn-danger btn-sm" ng-click="remove(note)">' +
                        '<i class="glyphicon glyphicon-remove"></i>' +
                        '</span> ' +
                    '</td> ' +

                '</tr> ' +
            '</tbody> ' +
        '</table>'
    };
}]);

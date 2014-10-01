'use strict';
angular.module('fmuClientApp')
.directive('fmuHandlingarTable', function(){
    return {
        scope: {
            handlingar: '=handlingar'
        },
        restrict: 'E',
        template: '\
        <table class="table table-bordered">\
            <tr>\
                <th>\
                    Handling\
                </th>\
                <th>\
                    Registrerad av\
                </th>\
                <th>\
                    Registrerad, datum\
                </th>\
            </tr>\
            <tr ng-repeat="doc in handlingar">\
                <td>{{doc.name}}</td>\
                <td>{{doc.regBy.name}}, {{doc.regBy.unit}}</td>\
                <td>{{doc.regDate}}</td>\
            </tr>\
        </table>'
    };
})
.directive('fmuTillaggTable', function(){
    return {
        scope: {
            tillagg: '=tillagg'
        },
        restrict: 'E',
        template: '\
        <table class="table table-bordered">\
            <tr>\
                <th>\
                    Handling\
                </th>\
                <th>Begäran skickad, av</th>\
                <th>Begäran skickad, datum</th>\
                <th>Begäran skickad till:</th>\
            </tr>\
            <tr ng-repeat="am in tillagg">\
                <td>{{am.name}}</td>\
                <td>{{am.reqBy.name}}, {{am.reqBy.unit}}</td>\
                <td>{{am.reqDate}}</td>\
                <td>{{am.reqTo.name}}, {{am.reqBy.unit}}</td>\
            </tr>\
        </table>\
        '
    };
});

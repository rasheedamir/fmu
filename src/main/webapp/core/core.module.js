(function() {
    'use strict';
    /**
     * common Module
     *
     * This module include all common utilities modules used accross the app.
     */
    angular.module('fmu.core', [
        'templatecache',
        'gettext',
        'ngResource',
        'ui.bootstrap',

        'util.exception',
        'util.logger',
        'util.router'
    ]).run(['gettextCatalog',
        function(gettextCatalog) {
            gettextCatalog.currentLanguage = 'sv';
            //gettextCatalog.debug = true;
        }
    ]);
})();
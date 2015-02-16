(function() {
    'use strict';

    /**
     * fmu Module
     *
     * The main module of fmu app
     */
    angular.module('fmu', [
    	// Common module dependencies
        'fmu.core',

        // Sub modules
        'fmu.login',
        'fmu.overview',
        'fmu.eavrop'
    ]);
})();
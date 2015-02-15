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
		
        'util.exception',
        'util.logger',
		]);
})();
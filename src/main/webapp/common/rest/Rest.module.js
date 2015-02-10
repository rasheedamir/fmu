(function() {
    'use strict';
    angular.module('RestModule', [
    		'ngResource'
    	])
        .factory('Dataservice', ['$resource', 'RESTURL',
            function(/*$resource, RESTURL*/) {
            	return {
            	// 	getIncomingEavrop: getIncomingEavrop
            	// };


            	// function getIncomingEavrop(fromdate, todate, status, page, pagesize, sortkey, sortorder) {

            	};
            }
        ]);
})();
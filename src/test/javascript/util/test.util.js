'use strict';
var getCalledURl;
beforeEach(function() {
    getCalledURl = function(backend) {
        var calledUrl;
        var PATTERN = /.*/;
        backend.whenGET(PATTERN).respond(function(method, url) {
            calledUrl = url;
            return [200, 'You called: ' + url];
        });

        backend.whenPUT(PATTERN).respond(function(method, url) {
            calledUrl = url;
            return [200, 'You called: ' + url];
        });

        backend.flush();
        
        return calledUrl;
    };
});
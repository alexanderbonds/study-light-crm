'use strict';

angular.module('chatService', [])
    .factory('chatService', ['$rootScope', '$stomp', '$q', function($rootScope, $stomp, $q) {

        var subscription;
        var ready;

        return {

            connect: function(destination, header, errorCallback) {
                var deferred = $q.defer();
                $stomp.setDebug(function (args) {
                    console.log(args + '\n');
                });
                $stomp.connect(destination, header, function (error) {
                    $rootScope.$apply(function(){
                        errorCallback(error);
                    });
                })
                    .then(function () {
                        deferred.resolve();
                    });

                return deferred.promise;
            },

            subscribe: function(destination, callback) {
                subscription = $stomp.subscribe(destination, function (payload, headers, res) {
                    $rootScope.$apply(function(){
                        callback(payload);
                    });
                });
            },

            send: function(destination, object) {
                $stomp.send(destination, object);
            },

            unsubscribe: function () {
                if (subscription != null) {
                    subscription.unsubscribe();
                }
            },

            disconnect: function () {
                $stomp.disconnect();
            }
        }

    }]);
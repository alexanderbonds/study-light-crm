angular.module('pageService', [])
    .factory('pageService', function($window) {
        var defaultTitle = 'Light CRM';

        return {
            setNotificationCount: function(count) {
                this.setTitle(defaultTitle);
                if (count > 0) {
                    this.setTitle(
                        this.defaultTitle() + ' (' + count + ')'
                    );
                }
            },

            setTitle: function (newTitle) {
                $window.document.title = newTitle;
            },

            title: function () {
                return $window.document.title;
            },

            defaultTitle: function () {
                return defaultTitle;
            }
        };
    });
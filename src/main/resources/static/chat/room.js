angular.module('app').controller('roomController',
    function ($scope, $rootScope, $http, $localStorage, chatService, $uibModal, pageService) {

        const contextPath = 'http://localhost:8180/app';
        $scope.senderId = $localStorage.currentUser.profileId;
        $scope.chats = [];
        $scope.activeChatWindow = false;
        $scope.pageNotificationCount = 0;

        $scope.subscribe = function() {
            chatService.ready.then(function () {
                chatService.unsubscribe();
                chatService.subscribe("/user/" + $scope.senderId + "/queue/messages", function (payload) {
                    $scope.getNotification(payload);
                });
            });
        };

        $scope.getNotification = function(payload) {
            $scope.playAudio();
            angular.forEach($scope.chats, function (value, key) {
                if (value.chatId === payload.chatId) {
                    $scope.chats[key].unreadMessageCount += 1;
                    $scope.pageNotificationCount += 1;
                    pageService.setNotificationCount($scope.pageNotificationCount);
                }
            });
        };

        $scope.playAudio = function() {
            var audio = new Audio('sounds/notification.mp3');
            audio.play();
        };

        $scope.getChats = function() {
            $scope.pageNotificationCount = 0;
            $http({
                url: contextPath + '/chats/rooms/' + $scope.senderId,
                method: 'GET'
            })
                .then(function (response) {
                    $scope.chats = response.data;
                    angular.forEach($scope.chats, function (value) {
                        $scope.pageNotificationCount += value.unreadMessageCount;
                        $http.get(contextPath + '/api/v1/files/photo/preview/' + value.recipientId,
                            {responseType: "arraybuffer"}
                        )
                            .then(function (response) {
                                let contentType = response.headers("content-type");
                                let blob = new Blob([response.data], {type: contentType});
                                value.recipientAvatar = (window.URL || window.webkitURL).createObjectURL(blob);
                            });
                    })
                    pageService.setNotificationCount($scope.pageNotificationCount);
                });
        };

        $scope.getChatById = function(chatID) {
            angular.forEach($scope.chats, function (value) {
                if (value.chatId === chatID) {
                    $scope.currentChat = value;
                    $scope.pageNotificationCount -= $scope.currentChat.unreadMessageCount;
                    $scope.currentChat.unreadMessageCount = 0;
                    pageService.setNotificationCount($scope.pageNotificationCount);
                }
            });
        };

        $scope.open = function (recipientId, currentChatId) {
            $scope.recipientId = recipientId;
            $scope.getChatById(currentChatId, recipientId);
            $scope.activeChatWindow = true;
            $uibModal.open({
                templateUrl: 'chat/chat.html',
                controller: 'chatController',
                windowClass: 'my-modal',
                size: 'lg',
                scope: $scope
                })
                .result.then(null, function () {
                    $scope.subscribe();
                    $scope.activeChatWindow = false;
                    $scope.getChats();
                 })
            };

        $scope.subscribe();
        $scope.getChats();

    });
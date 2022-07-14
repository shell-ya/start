package com.starnft.star.notice.interfaces;


import com.starnft.star.api.notice.NotificationDeliveryGrpc;
import com.starnft.star.api.notice.req.MessageSendRequest;
import com.starnft.star.api.notice.res.MessageSendResponse;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;

@GrpcService
public class NotificationDelivery extends NotificationDeliveryGrpc.NotificationDeliveryImplBase {


    @Override
    public void sendStandalone(MessageSendRequest request, StreamObserver<MessageSendResponse> responseObserver) {
        super.sendStandalone(request, responseObserver);
    }
}

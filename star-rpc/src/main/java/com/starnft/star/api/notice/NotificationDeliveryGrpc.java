package com.starnft.star.api.notice;

import com.starnft.star.api.notice.req.MessageSendRequest;
import com.starnft.star.api.notice.res.MessageSendResponse;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 *
 */
@javax.annotation.Generated(
        value = "by gRPC proto compiler (version 1.46.0)",
        comments = "Source: NotificationApp.proto")
@io.grpc.stub.annotations.GrpcGenerated
public final class NotificationDeliveryGrpc {

    private NotificationDeliveryGrpc() {
    }

    public static final String SERVICE_NAME = "com.starnft.star.api.notice.NotificationDelivery";

    // Static method descriptors that strictly reflect the proto.
    private static volatile io.grpc.MethodDescriptor<MessageSendRequest,
            MessageSendResponse> getSendStandaloneMethod;

    @io.grpc.stub.annotations.RpcMethod(
            fullMethodName = SERVICE_NAME + '/' + "sendStandalone",
            requestType = MessageSendRequest.class,
            responseType = MessageSendResponse.class,
            methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
    public static io.grpc.MethodDescriptor<MessageSendRequest,
            MessageSendResponse> getSendStandaloneMethod() {
        io.grpc.MethodDescriptor<MessageSendRequest, MessageSendResponse> getSendStandaloneMethod;
        if ((getSendStandaloneMethod = NotificationDeliveryGrpc.getSendStandaloneMethod) == null) {
            synchronized (NotificationDeliveryGrpc.class) {
                if ((getSendStandaloneMethod = NotificationDeliveryGrpc.getSendStandaloneMethod) == null) {
                    NotificationDeliveryGrpc.getSendStandaloneMethod = getSendStandaloneMethod =
                            io.grpc.MethodDescriptor.<MessageSendRequest, MessageSendResponse>newBuilder()
                                    .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
                                    .setFullMethodName(generateFullMethodName(SERVICE_NAME, "sendStandalone"))
                                    .setSampledToLocalTracing(true)
                                    .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                                            MessageSendRequest.getDefaultInstance()))
                                    .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                                            MessageSendResponse.getDefaultInstance()))
                                    .setSchemaDescriptor(new NotificationDeliveryMethodDescriptorSupplier("sendStandalone"))
                                    .build();
                }
            }
        }
        return getSendStandaloneMethod;
    }

    /**
     * Creates a new async stub that supports all call types for the service
     */
    public static NotificationDeliveryStub newStub(io.grpc.Channel channel) {
        io.grpc.stub.AbstractStub.StubFactory<NotificationDeliveryStub> factory =
                new io.grpc.stub.AbstractStub.StubFactory<NotificationDeliveryStub>() {
                    @Override
                    public NotificationDeliveryStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
                        return new NotificationDeliveryStub(channel, callOptions);
                    }
                };
        return NotificationDeliveryStub.newStub(factory, channel);
    }

    /**
     * Creates a new blocking-style stub that supports unary and streaming output calls on the service
     */
    public static NotificationDeliveryBlockingStub newBlockingStub(
            io.grpc.Channel channel) {
        io.grpc.stub.AbstractStub.StubFactory<NotificationDeliveryBlockingStub> factory =
                new io.grpc.stub.AbstractStub.StubFactory<NotificationDeliveryBlockingStub>() {
                    @Override
                    public NotificationDeliveryBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
                        return new NotificationDeliveryBlockingStub(channel, callOptions);
                    }
                };
        return NotificationDeliveryBlockingStub.newStub(factory, channel);
    }

    /**
     * Creates a new ListenableFuture-style stub that supports unary calls on the service
     */
    public static NotificationDeliveryFutureStub newFutureStub(
            io.grpc.Channel channel) {
        io.grpc.stub.AbstractStub.StubFactory<NotificationDeliveryFutureStub> factory =
                new io.grpc.stub.AbstractStub.StubFactory<NotificationDeliveryFutureStub>() {
                    @Override
                    public NotificationDeliveryFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
                        return new NotificationDeliveryFutureStub(channel, callOptions);
                    }
                };
        return NotificationDeliveryFutureStub.newStub(factory, channel);
    }

    /**
     *
     */
    public static abstract class NotificationDeliveryImplBase implements io.grpc.BindableService {

        /**
         *
         */
        public void sendStandalone(MessageSendRequest request,
                                   io.grpc.stub.StreamObserver<MessageSendResponse> responseObserver) {
            io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getSendStandaloneMethod(), responseObserver);
        }

        @Override
        public final io.grpc.ServerServiceDefinition bindService() {
            return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
                    .addMethod(
                            getSendStandaloneMethod(),
                            io.grpc.stub.ServerCalls.asyncUnaryCall(
                                    new MethodHandlers<
                                            MessageSendRequest,
                                            MessageSendResponse>(
                                            this, METHODID_SEND_STANDALONE)))
                    .build();
        }
    }

    /**
     *
     */
    public static final class NotificationDeliveryStub extends io.grpc.stub.AbstractAsyncStub<NotificationDeliveryStub> {
        private NotificationDeliveryStub(
                io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
            super(channel, callOptions);
        }

        @Override
        protected NotificationDeliveryStub build(
                io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
            return new NotificationDeliveryStub(channel, callOptions);
        }

        /**
         *
         */
        public void sendStandalone(MessageSendRequest request,
                                   io.grpc.stub.StreamObserver<MessageSendResponse> responseObserver) {
            io.grpc.stub.ClientCalls.asyncUnaryCall(
                    getChannel().newCall(getSendStandaloneMethod(), getCallOptions()), request, responseObserver);
        }
    }

    /**
     *
     */
    public static final class NotificationDeliveryBlockingStub extends io.grpc.stub.AbstractBlockingStub<NotificationDeliveryBlockingStub> {
        private NotificationDeliveryBlockingStub(
                io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
            super(channel, callOptions);
        }

        @Override
        protected NotificationDeliveryBlockingStub build(
                io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
            return new NotificationDeliveryBlockingStub(channel, callOptions);
        }

        /**
         *
         */
        public MessageSendResponse sendStandalone(MessageSendRequest request) {
            return io.grpc.stub.ClientCalls.blockingUnaryCall(
                    getChannel(), getSendStandaloneMethod(), getCallOptions(), request);
        }
    }

    /**
     *
     */
    public static final class NotificationDeliveryFutureStub extends io.grpc.stub.AbstractFutureStub<NotificationDeliveryFutureStub> {
        private NotificationDeliveryFutureStub(
                io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
            super(channel, callOptions);
        }

        @Override
        protected NotificationDeliveryFutureStub build(
                io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
            return new NotificationDeliveryFutureStub(channel, callOptions);
        }

        /**
         *
         */
        public com.google.common.util.concurrent.ListenableFuture<MessageSendResponse> sendStandalone(
                MessageSendRequest request) {
            return io.grpc.stub.ClientCalls.futureUnaryCall(
                    getChannel().newCall(getSendStandaloneMethod(), getCallOptions()), request);
        }
    }

    private static final int METHODID_SEND_STANDALONE = 0;

    private static final class MethodHandlers<Req, Resp> implements
            io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
            io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
            io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
            io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
        private final NotificationDeliveryImplBase serviceImpl;
        private final int methodId;

        MethodHandlers(NotificationDeliveryImplBase serviceImpl, int methodId) {
            this.serviceImpl = serviceImpl;
            this.methodId = methodId;
        }

        @Override
        @SuppressWarnings("unchecked")
        public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
            switch (methodId) {
                case METHODID_SEND_STANDALONE:
                    serviceImpl.sendStandalone((MessageSendRequest) request,
                            (io.grpc.stub.StreamObserver<MessageSendResponse>) responseObserver);
                    break;
                default:
                    throw new AssertionError();
            }
        }

        @Override
        @SuppressWarnings("unchecked")
        public io.grpc.stub.StreamObserver<Req> invoke(
                io.grpc.stub.StreamObserver<Resp> responseObserver) {
            switch (methodId) {
                default:
                    throw new AssertionError();
            }
        }
    }

    private static abstract class NotificationDeliveryBaseDescriptorSupplier
            implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
        NotificationDeliveryBaseDescriptorSupplier() {
        }

        @Override
        public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
            return com.starnft.star.api.notice.NotificationApp.getDescriptor();
        }

        @Override
        public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
            return getFileDescriptor().findServiceByName("NotificationDelivery");
        }
    }

    private static final class NotificationDeliveryFileDescriptorSupplier
            extends NotificationDeliveryBaseDescriptorSupplier {
        NotificationDeliveryFileDescriptorSupplier() {
        }
    }

    private static final class NotificationDeliveryMethodDescriptorSupplier
            extends NotificationDeliveryBaseDescriptorSupplier
            implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
        private final String methodName;

        NotificationDeliveryMethodDescriptorSupplier(String methodName) {
            this.methodName = methodName;
        }

        @Override
        public com.google.protobuf.Descriptors.MethodDescriptor getMethodDescriptor() {
            return getServiceDescriptor().findMethodByName(methodName);
        }
    }

    private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

    public static io.grpc.ServiceDescriptor getServiceDescriptor() {
        io.grpc.ServiceDescriptor result = serviceDescriptor;
        if (result == null) {
            synchronized (NotificationDeliveryGrpc.class) {
                result = serviceDescriptor;
                if (result == null) {
                    serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
                            .setSchemaDescriptor(new NotificationDeliveryFileDescriptorSupplier())
                            .addMethod(getSendStandaloneMethod())
                            .build();
                }
            }
        }
        return result;
    }
}

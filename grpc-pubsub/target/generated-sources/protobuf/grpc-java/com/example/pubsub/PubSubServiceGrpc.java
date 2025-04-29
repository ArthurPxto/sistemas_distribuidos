package com.example.pubsub;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.64.0)",
    comments = "Source: pubsub.proto")
@io.grpc.stub.annotations.GrpcGenerated
public final class PubSubServiceGrpc {

  private PubSubServiceGrpc() {}

  public static final java.lang.String SERVICE_NAME = "pubsub.PubSubService";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<com.example.pubsub.SubscribeRequest,
      com.example.pubsub.Message> getSubscribeMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "Subscribe",
      requestType = com.example.pubsub.SubscribeRequest.class,
      responseType = com.example.pubsub.Message.class,
      methodType = io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING)
  public static io.grpc.MethodDescriptor<com.example.pubsub.SubscribeRequest,
      com.example.pubsub.Message> getSubscribeMethod() {
    io.grpc.MethodDescriptor<com.example.pubsub.SubscribeRequest, com.example.pubsub.Message> getSubscribeMethod;
    if ((getSubscribeMethod = PubSubServiceGrpc.getSubscribeMethod) == null) {
      synchronized (PubSubServiceGrpc.class) {
        if ((getSubscribeMethod = PubSubServiceGrpc.getSubscribeMethod) == null) {
          PubSubServiceGrpc.getSubscribeMethod = getSubscribeMethod =
              io.grpc.MethodDescriptor.<com.example.pubsub.SubscribeRequest, com.example.pubsub.Message>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "Subscribe"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.example.pubsub.SubscribeRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.example.pubsub.Message.getDefaultInstance()))
              .setSchemaDescriptor(new PubSubServiceMethodDescriptorSupplier("Subscribe"))
              .build();
        }
      }
    }
    return getSubscribeMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.example.pubsub.Message,
      com.example.pubsub.PublishResponse> getPublishMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "Publish",
      requestType = com.example.pubsub.Message.class,
      responseType = com.example.pubsub.PublishResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.example.pubsub.Message,
      com.example.pubsub.PublishResponse> getPublishMethod() {
    io.grpc.MethodDescriptor<com.example.pubsub.Message, com.example.pubsub.PublishResponse> getPublishMethod;
    if ((getPublishMethod = PubSubServiceGrpc.getPublishMethod) == null) {
      synchronized (PubSubServiceGrpc.class) {
        if ((getPublishMethod = PubSubServiceGrpc.getPublishMethod) == null) {
          PubSubServiceGrpc.getPublishMethod = getPublishMethod =
              io.grpc.MethodDescriptor.<com.example.pubsub.Message, com.example.pubsub.PublishResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "Publish"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.example.pubsub.Message.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.example.pubsub.PublishResponse.getDefaultInstance()))
              .setSchemaDescriptor(new PubSubServiceMethodDescriptorSupplier("Publish"))
              .build();
        }
      }
    }
    return getPublishMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static PubSubServiceStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<PubSubServiceStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<PubSubServiceStub>() {
        @java.lang.Override
        public PubSubServiceStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new PubSubServiceStub(channel, callOptions);
        }
      };
    return PubSubServiceStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static PubSubServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<PubSubServiceBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<PubSubServiceBlockingStub>() {
        @java.lang.Override
        public PubSubServiceBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new PubSubServiceBlockingStub(channel, callOptions);
        }
      };
    return PubSubServiceBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static PubSubServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<PubSubServiceFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<PubSubServiceFutureStub>() {
        @java.lang.Override
        public PubSubServiceFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new PubSubServiceFutureStub(channel, callOptions);
        }
      };
    return PubSubServiceFutureStub.newStub(factory, channel);
  }

  /**
   */
  public interface AsyncService {

    /**
     */
    default void subscribe(com.example.pubsub.SubscribeRequest request,
        io.grpc.stub.StreamObserver<com.example.pubsub.Message> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getSubscribeMethod(), responseObserver);
    }

    /**
     */
    default void publish(com.example.pubsub.Message request,
        io.grpc.stub.StreamObserver<com.example.pubsub.PublishResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getPublishMethod(), responseObserver);
    }
  }

  /**
   * Base class for the server implementation of the service PubSubService.
   */
  public static abstract class PubSubServiceImplBase
      implements io.grpc.BindableService, AsyncService {

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return PubSubServiceGrpc.bindService(this);
    }
  }

  /**
   * A stub to allow clients to do asynchronous rpc calls to service PubSubService.
   */
  public static final class PubSubServiceStub
      extends io.grpc.stub.AbstractAsyncStub<PubSubServiceStub> {
    private PubSubServiceStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected PubSubServiceStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new PubSubServiceStub(channel, callOptions);
    }

    /**
     */
    public void subscribe(com.example.pubsub.SubscribeRequest request,
        io.grpc.stub.StreamObserver<com.example.pubsub.Message> responseObserver) {
      io.grpc.stub.ClientCalls.asyncServerStreamingCall(
          getChannel().newCall(getSubscribeMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void publish(com.example.pubsub.Message request,
        io.grpc.stub.StreamObserver<com.example.pubsub.PublishResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getPublishMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   * A stub to allow clients to do synchronous rpc calls to service PubSubService.
   */
  public static final class PubSubServiceBlockingStub
      extends io.grpc.stub.AbstractBlockingStub<PubSubServiceBlockingStub> {
    private PubSubServiceBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected PubSubServiceBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new PubSubServiceBlockingStub(channel, callOptions);
    }

    /**
     */
    public java.util.Iterator<com.example.pubsub.Message> subscribe(
        com.example.pubsub.SubscribeRequest request) {
      return io.grpc.stub.ClientCalls.blockingServerStreamingCall(
          getChannel(), getSubscribeMethod(), getCallOptions(), request);
    }

    /**
     */
    public com.example.pubsub.PublishResponse publish(com.example.pubsub.Message request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getPublishMethod(), getCallOptions(), request);
    }
  }

  /**
   * A stub to allow clients to do ListenableFuture-style rpc calls to service PubSubService.
   */
  public static final class PubSubServiceFutureStub
      extends io.grpc.stub.AbstractFutureStub<PubSubServiceFutureStub> {
    private PubSubServiceFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected PubSubServiceFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new PubSubServiceFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.example.pubsub.PublishResponse> publish(
        com.example.pubsub.Message request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getPublishMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_SUBSCRIBE = 0;
  private static final int METHODID_PUBLISH = 1;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final AsyncService serviceImpl;
    private final int methodId;

    MethodHandlers(AsyncService serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_SUBSCRIBE:
          serviceImpl.subscribe((com.example.pubsub.SubscribeRequest) request,
              (io.grpc.stub.StreamObserver<com.example.pubsub.Message>) responseObserver);
          break;
        case METHODID_PUBLISH:
          serviceImpl.publish((com.example.pubsub.Message) request,
              (io.grpc.stub.StreamObserver<com.example.pubsub.PublishResponse>) responseObserver);
          break;
        default:
          throw new AssertionError();
      }
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public io.grpc.stub.StreamObserver<Req> invoke(
        io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        default:
          throw new AssertionError();
      }
    }
  }

  public static final io.grpc.ServerServiceDefinition bindService(AsyncService service) {
    return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
        .addMethod(
          getSubscribeMethod(),
          io.grpc.stub.ServerCalls.asyncServerStreamingCall(
            new MethodHandlers<
              com.example.pubsub.SubscribeRequest,
              com.example.pubsub.Message>(
                service, METHODID_SUBSCRIBE)))
        .addMethod(
          getPublishMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              com.example.pubsub.Message,
              com.example.pubsub.PublishResponse>(
                service, METHODID_PUBLISH)))
        .build();
  }

  private static abstract class PubSubServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    PubSubServiceBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return com.example.pubsub.PubSubProto.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("PubSubService");
    }
  }

  private static final class PubSubServiceFileDescriptorSupplier
      extends PubSubServiceBaseDescriptorSupplier {
    PubSubServiceFileDescriptorSupplier() {}
  }

  private static final class PubSubServiceMethodDescriptorSupplier
      extends PubSubServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final java.lang.String methodName;

    PubSubServiceMethodDescriptorSupplier(java.lang.String methodName) {
      this.methodName = methodName;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.MethodDescriptor getMethodDescriptor() {
      return getServiceDescriptor().findMethodByName(methodName);
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (PubSubServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new PubSubServiceFileDescriptorSupplier())
              .addMethod(getSubscribeMethod())
              .addMethod(getPublishMethod())
              .build();
        }
      }
    }
    return result;
  }
}

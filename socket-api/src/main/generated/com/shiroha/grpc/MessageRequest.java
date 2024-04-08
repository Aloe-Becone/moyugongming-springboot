// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: MessageExchange.proto

package com.shiroha.grpc;

/**
 * Protobuf type {@code messageexchange.MessageRequest}
 */
public final class MessageRequest extends
        com.google.protobuf.GeneratedMessageV3 implements
        // @@protoc_insertion_point(message_implements:messageexchange.MessageRequest)
        MessageRequestOrBuilder {
    private static final long serialVersionUID = 0L;

    // Use MessageRequest.newBuilder() to construct.
    private MessageRequest(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
        super(builder);
    }

    private MessageRequest() {
        binaryImage_ = com.google.protobuf.ByteString.EMPTY;
    }

    public static final int BINARY_IMAGE_FIELD_NUMBER = 1;
    public static final int WIDTH_FIELD_NUMBER = 2;

    private MessageRequest(
            com.google.protobuf.CodedInputStream input,
            com.google.protobuf.ExtensionRegistryLite extensionRegistry)
            throws com.google.protobuf.InvalidProtocolBufferException {
        this();
        if (extensionRegistry == null) {
            throw new java.lang.NullPointerException();
        }
        com.google.protobuf.UnknownFieldSet.Builder unknownFields =
                com.google.protobuf.UnknownFieldSet.newBuilder();
        try {
            boolean done = false;
            while (!done) {
                int tag = input.readTag();
                switch (tag) {
                    case 0:
                        done = true;
                        break;
                    case 10: {

                        binaryImage_ = input.readBytes();
                        break;
                    }
                    case 16: {

                        width_ = input.readInt32();
                        break;
                    }
                    case 24: {

                        height_ = input.readInt32();
                        break;
                    }
                    default: {
                        if (!parseUnknownField(
                                input, unknownFields, extensionRegistry, tag)) {
                            done = true;
                        }
                        break;
                    }
                }
            }
        } catch (com.google.protobuf.InvalidProtocolBufferException e) {
            throw e.setUnfinishedMessage(this);
        } catch (java.io.IOException e) {
            throw new com.google.protobuf.InvalidProtocolBufferException(
                    e).setUnfinishedMessage(this);
        } finally {
            this.unknownFields = unknownFields.build();
            makeExtensionsImmutable();
        }
    }
    public static final int HEIGHT_FIELD_NUMBER = 3;

    @java.lang.Override
    protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
    internalGetFieldAccessorTable() {
        return com.shiroha.grpc.MessageExchangeProto.internal_static_messageexchange_MessageRequest_fieldAccessorTable
                .ensureFieldAccessorsInitialized(
                        com.shiroha.grpc.MessageRequest.class, com.shiroha.grpc.MessageRequest.Builder.class);
    }
    // @@protoc_insertion_point(class_scope:messageexchange.MessageRequest)
    private static final com.shiroha.grpc.MessageRequest DEFAULT_INSTANCE;
    private static final com.google.protobuf.Parser<MessageRequest>
            PARSER = new com.google.protobuf.AbstractParser<MessageRequest>() {
        @java.lang.Override
        public MessageRequest parsePartialFrom(
                com.google.protobuf.CodedInputStream input,
                com.google.protobuf.ExtensionRegistryLite extensionRegistry)
                throws com.google.protobuf.InvalidProtocolBufferException {
            return new MessageRequest(input, extensionRegistry);
        }
    };

    /**
     * <code>bytes binary_image = 1;</code>
     *
     * @return The binaryImage.
     */
    public com.google.protobuf.ByteString getBinaryImage() {
        return binaryImage_;
    }

    static {
        DEFAULT_INSTANCE = new com.shiroha.grpc.MessageRequest();
    }

    private com.google.protobuf.ByteString binaryImage_;

    /**
     * <code>int32 width = 2;</code>
     *
     * @return The width.
     */
    public int getWidth() {
        return width_;
    }
    private int width_;
    private int height_;

    /**
     * <code>int32 height = 3;</code>
     *
     * @return The height.
     */
    public int getHeight() {
        return height_;
    }

    private byte memoizedIsInitialized = -1;

    public static final com.google.protobuf.Descriptors.Descriptor
    getDescriptor() {
        return com.shiroha.grpc.MessageExchangeProto.internal_static_messageexchange_MessageRequest_descriptor;
    }

    public static com.shiroha.grpc.MessageRequest getDefaultInstance() {
        return DEFAULT_INSTANCE;
    }

    public static com.google.protobuf.Parser<MessageRequest> parser() {
        return PARSER;
    }

    @java.lang.Override
    @SuppressWarnings({"unused"})
    protected java.lang.Object newInstance(
            UnusedPrivateParameter unused) {
        return new MessageRequest();
    }

    @java.lang.Override
    public final com.google.protobuf.UnknownFieldSet
    getUnknownFields() {
        return this.unknownFields;
    }

    public static com.shiroha.grpc.MessageRequest parseFrom(
            java.nio.ByteBuffer data)
            throws com.google.protobuf.InvalidProtocolBufferException {
        return PARSER.parseFrom(data);
    }

    public static com.shiroha.grpc.MessageRequest parseFrom(
            java.nio.ByteBuffer data,
            com.google.protobuf.ExtensionRegistryLite extensionRegistry)
            throws com.google.protobuf.InvalidProtocolBufferException {
        return PARSER.parseFrom(data, extensionRegistry);
    }

    public static com.shiroha.grpc.MessageRequest parseFrom(
            com.google.protobuf.ByteString data)
            throws com.google.protobuf.InvalidProtocolBufferException {
        return PARSER.parseFrom(data);
    }

    public static com.shiroha.grpc.MessageRequest parseFrom(
            com.google.protobuf.ByteString data,
            com.google.protobuf.ExtensionRegistryLite extensionRegistry)
            throws com.google.protobuf.InvalidProtocolBufferException {
        return PARSER.parseFrom(data, extensionRegistry);
    }

    public static com.shiroha.grpc.MessageRequest parseFrom(byte[] data)
            throws com.google.protobuf.InvalidProtocolBufferException {
        return PARSER.parseFrom(data);
    }

    public static com.shiroha.grpc.MessageRequest parseFrom(
            byte[] data,
            com.google.protobuf.ExtensionRegistryLite extensionRegistry)
            throws com.google.protobuf.InvalidProtocolBufferException {
        return PARSER.parseFrom(data, extensionRegistry);
    }

    public static com.shiroha.grpc.MessageRequest parseFrom(java.io.InputStream input)
            throws java.io.IOException {
        return com.google.protobuf.GeneratedMessageV3
                .parseWithIOException(PARSER, input);
    }

    public static com.shiroha.grpc.MessageRequest parseFrom(
            java.io.InputStream input,
            com.google.protobuf.ExtensionRegistryLite extensionRegistry)
            throws java.io.IOException {
        return com.google.protobuf.GeneratedMessageV3
                .parseWithIOException(PARSER, input, extensionRegistry);
    }

    public static com.shiroha.grpc.MessageRequest parseDelimitedFrom(java.io.InputStream input)
            throws java.io.IOException {
        return com.google.protobuf.GeneratedMessageV3
                .parseDelimitedWithIOException(PARSER, input);
    }

    public static com.shiroha.grpc.MessageRequest parseDelimitedFrom(
            java.io.InputStream input,
            com.google.protobuf.ExtensionRegistryLite extensionRegistry)
            throws java.io.IOException {
        return com.google.protobuf.GeneratedMessageV3
                .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
    }

    public static com.shiroha.grpc.MessageRequest parseFrom(
            com.google.protobuf.CodedInputStream input)
            throws java.io.IOException {
        return com.google.protobuf.GeneratedMessageV3
                .parseWithIOException(PARSER, input);
    }

    public static com.shiroha.grpc.MessageRequest parseFrom(
            com.google.protobuf.CodedInputStream input,
            com.google.protobuf.ExtensionRegistryLite extensionRegistry)
            throws java.io.IOException {
        return com.google.protobuf.GeneratedMessageV3
                .parseWithIOException(PARSER, input, extensionRegistry);
    }

    @java.lang.Override
    public final boolean isInitialized() {
        byte isInitialized = memoizedIsInitialized;
        if (isInitialized == 1) return true;
        if (isInitialized == 0) return false;

        memoizedIsInitialized = 1;
        return true;
    }

    public static Builder newBuilder() {
        return DEFAULT_INSTANCE.toBuilder();
    }

    public static Builder newBuilder(com.shiroha.grpc.MessageRequest prototype) {
        return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
    }

    @java.lang.Override
    public Builder toBuilder() {
        return this == DEFAULT_INSTANCE
                ? new Builder() : new Builder().mergeFrom(this);
    }

    @java.lang.Override
    protected Builder newBuilderForType(
            com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
        Builder builder = new Builder(parent);
        return builder;
    }

    @java.lang.Override
    public void writeTo(com.google.protobuf.CodedOutputStream output)
            throws java.io.IOException {
        if (!binaryImage_.isEmpty()) {
            output.writeBytes(1, binaryImage_);
        }
        if (width_ != 0) {
            output.writeInt32(2, width_);
        }
        if (height_ != 0) {
            output.writeInt32(3, height_);
        }
        unknownFields.writeTo(output);
    }

    @java.lang.Override
    public int getSerializedSize() {
        int size = memoizedSize;
        if (size != -1) return size;

        size = 0;
        if (!binaryImage_.isEmpty()) {
            size += com.google.protobuf.CodedOutputStream
                    .computeBytesSize(1, binaryImage_);
        }
        if (width_ != 0) {
            size += com.google.protobuf.CodedOutputStream
                    .computeInt32Size(2, width_);
        }
        if (height_ != 0) {
            size += com.google.protobuf.CodedOutputStream
                    .computeInt32Size(3, height_);
        }
        size += unknownFields.getSerializedSize();
        memoizedSize = size;
        return size;
    }

    @java.lang.Override
    public boolean equals(final java.lang.Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof com.shiroha.grpc.MessageRequest)) {
            return super.equals(obj);
        }
        com.shiroha.grpc.MessageRequest other = (com.shiroha.grpc.MessageRequest) obj;

        if (!getBinaryImage()
                .equals(other.getBinaryImage())) return false;
        if (getWidth()
                != other.getWidth()) return false;
        if (getHeight()
                != other.getHeight()) return false;
        if (!unknownFields.equals(other.unknownFields)) return false;
        return true;
    }

    @java.lang.Override
    public int hashCode() {
        if (memoizedHashCode != 0) {
            return memoizedHashCode;
        }
        int hash = 41;
        hash = (19 * hash) + getDescriptor().hashCode();
        hash = (37 * hash) + BINARY_IMAGE_FIELD_NUMBER;
        hash = (53 * hash) + getBinaryImage().hashCode();
        hash = (37 * hash) + WIDTH_FIELD_NUMBER;
        hash = (53 * hash) + getWidth();
        hash = (37 * hash) + HEIGHT_FIELD_NUMBER;
        hash = (53 * hash) + getHeight();
        hash = (29 * hash) + unknownFields.hashCode();
        memoizedHashCode = hash;
        return hash;
    }

    @java.lang.Override
    public Builder newBuilderForType() {
        return newBuilder();
    }

    @java.lang.Override
  public com.google.protobuf.Parser<MessageRequest> getParserForType() {
    return PARSER;
  }

  @java.lang.Override
  public com.shiroha.grpc.MessageRequest getDefaultInstanceForType() {
    return DEFAULT_INSTANCE;
  }

    /**
     * Protobuf type {@code messageexchange.MessageRequest}
     */
    public static final class Builder extends
            com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements
            // @@protoc_insertion_point(builder_implements:messageexchange.MessageRequest)
            com.shiroha.grpc.MessageRequestOrBuilder {
        public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
            return com.shiroha.grpc.MessageExchangeProto.internal_static_messageexchange_MessageRequest_descriptor;
        }

        @java.lang.Override
        protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
        internalGetFieldAccessorTable() {
            return com.shiroha.grpc.MessageExchangeProto.internal_static_messageexchange_MessageRequest_fieldAccessorTable
                    .ensureFieldAccessorsInitialized(
                            com.shiroha.grpc.MessageRequest.class, com.shiroha.grpc.MessageRequest.Builder.class);
        }

        private com.google.protobuf.ByteString binaryImage_ = com.google.protobuf.ByteString.EMPTY;
        private int width_;

        private void maybeForceBuilderInitialization() {
            if (com.google.protobuf.GeneratedMessageV3
                    .alwaysUseFieldBuilders) {
            }
        }
        private int height_;

        @java.lang.Override
        public com.google.protobuf.Descriptors.Descriptor
        getDescriptorForType() {
            return com.shiroha.grpc.MessageExchangeProto.internal_static_messageexchange_MessageRequest_descriptor;
        }

        @java.lang.Override
        public com.shiroha.grpc.MessageRequest getDefaultInstanceForType() {
            return com.shiroha.grpc.MessageRequest.getDefaultInstance();
        }

        @java.lang.Override
        public com.shiroha.grpc.MessageRequest build() {
            com.shiroha.grpc.MessageRequest result = buildPartial();
            if (!result.isInitialized()) {
                throw newUninitializedMessageException(result);
            }
            return result;
        }

        // Construct using com.shiroha.grpc.MessageRequest.newBuilder()
        private Builder() {
            maybeForceBuilderInitialization();
        }

        @java.lang.Override
        public Builder clone() {
            return super.clone();
        }

        @java.lang.Override
        public Builder setField(
                com.google.protobuf.Descriptors.FieldDescriptor field,
                java.lang.Object value) {
            return super.setField(field, value);
        }

        @java.lang.Override
        public Builder clearField(
                com.google.protobuf.Descriptors.FieldDescriptor field) {
            return super.clearField(field);
        }

        @java.lang.Override
        public Builder clearOneof(
                com.google.protobuf.Descriptors.OneofDescriptor oneof) {
            return super.clearOneof(oneof);
        }

        @java.lang.Override
        public Builder setRepeatedField(
                com.google.protobuf.Descriptors.FieldDescriptor field,
                int index, java.lang.Object value) {
            return super.setRepeatedField(field, index, value);
        }

        @java.lang.Override
        public Builder addRepeatedField(
                com.google.protobuf.Descriptors.FieldDescriptor field,
                java.lang.Object value) {
            return super.addRepeatedField(field, value);
        }

        @java.lang.Override
        public Builder mergeFrom(com.google.protobuf.Message other) {
            if (other instanceof com.shiroha.grpc.MessageRequest) {
                return mergeFrom((com.shiroha.grpc.MessageRequest) other);
            } else {
                super.mergeFrom(other);
                return this;
            }
        }

        private Builder(
                com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
            super(parent);
            maybeForceBuilderInitialization();
        }

        @java.lang.Override
        public final boolean isInitialized() {
            return true;
        }

        @java.lang.Override
        public Builder mergeFrom(
                com.google.protobuf.CodedInputStream input,
                com.google.protobuf.ExtensionRegistryLite extensionRegistry)
                throws java.io.IOException {
            com.shiroha.grpc.MessageRequest parsedMessage = null;
            try {
                parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
            } catch (com.google.protobuf.InvalidProtocolBufferException e) {
                parsedMessage = (com.shiroha.grpc.MessageRequest) e.getUnfinishedMessage();
                throw e.unwrapIOException();
            } finally {
                if (parsedMessage != null) {
                    mergeFrom(parsedMessage);
                }
            }
            return this;
        }

        @java.lang.Override
        public Builder clear() {
            super.clear();
            binaryImage_ = com.google.protobuf.ByteString.EMPTY;

            width_ = 0;

            height_ = 0;

            return this;
        }

        /**
         * <code>bytes binary_image = 1;</code>
         *
         * @return The binaryImage.
         */
        public com.google.protobuf.ByteString getBinaryImage() {
            return binaryImage_;
        }

        /**
         * <code>bytes binary_image = 1;</code>
         *
         * @param value The binaryImage to set.
         * @return This builder for chaining.
         */
        public Builder setBinaryImage(com.google.protobuf.ByteString value) {
            if (value == null) {
                throw new NullPointerException();
            }

            binaryImage_ = value;
            onChanged();
            return this;
        }

        /**
         * <code>bytes binary_image = 1;</code>
         *
         * @return This builder for chaining.
         */
        public Builder clearBinaryImage() {

            binaryImage_ = getDefaultInstance().getBinaryImage();
            onChanged();
            return this;
        }

        @java.lang.Override
        public com.shiroha.grpc.MessageRequest buildPartial() {
            com.shiroha.grpc.MessageRequest result = new com.shiroha.grpc.MessageRequest(this);
            result.binaryImage_ = binaryImage_;
            result.width_ = width_;
            result.height_ = height_;
            onBuilt();
            return result;
        }

        /**
         * <code>int32 width = 2;</code>
         *
         * @return The width.
         */
        public int getWidth() {
            return width_;
        }

        /**
         * <code>int32 width = 2;</code>
         *
         * @param value The width to set.
         * @return This builder for chaining.
         */
        public Builder setWidth(int value) {

            width_ = value;
            onChanged();
            return this;
        }

        /**
         * <code>int32 width = 2;</code>
         * @return This builder for chaining.
         */
        public Builder clearWidth() {

            width_ = 0;
            onChanged();
            return this;
        }

        public Builder mergeFrom(com.shiroha.grpc.MessageRequest other) {
            if (other == com.shiroha.grpc.MessageRequest.getDefaultInstance()) return this;
            if (other.getBinaryImage() != com.google.protobuf.ByteString.EMPTY) {
                setBinaryImage(other.getBinaryImage());
            }
            if (other.getWidth() != 0) {
                setWidth(other.getWidth());
            }
            if (other.getHeight() != 0) {
                setHeight(other.getHeight());
            }
            this.mergeUnknownFields(other.unknownFields);
            onChanged();
            return this;
        }

        /**
         * <code>int32 height = 3;</code>
         *
         * @return The height.
         */
        public int getHeight() {
            return height_;
        }

        /**
         * <code>int32 height = 3;</code>
         *
         * @param value The height to set.
         * @return This builder for chaining.
         */
        public Builder setHeight(int value) {

            height_ = value;
            onChanged();
            return this;
        }

        /**
         * <code>int32 height = 3;</code>
         *
         * @return This builder for chaining.
         */
        public Builder clearHeight() {

            height_ = 0;
            onChanged();
            return this;
        }

        @java.lang.Override
        public final Builder setUnknownFields(
                final com.google.protobuf.UnknownFieldSet unknownFields) {
            return super.setUnknownFields(unknownFields);
        }

        @java.lang.Override
        public final Builder mergeUnknownFields(
                final com.google.protobuf.UnknownFieldSet unknownFields) {
            return super.mergeUnknownFields(unknownFields);
        }


        // @@protoc_insertion_point(builder_scope:messageexchange.MessageRequest)
    }

}


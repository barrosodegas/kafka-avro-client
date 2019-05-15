/**
 * Autogenerated by Avro
 *
 * DO NOT EDIT DIRECTLY
 */
package br.com.barroso.kafka.avroclient.avro;

import org.apache.avro.specific.SpecificData;
import org.apache.avro.message.BinaryMessageEncoder;
import org.apache.avro.message.BinaryMessageDecoder;
import org.apache.avro.message.SchemaStore;

@SuppressWarnings("all")
@org.apache.avro.specific.AvroGenerated
public class Product extends org.apache.avro.specific.SpecificRecordBase implements org.apache.avro.specific.SpecificRecord {
  private static final long serialVersionUID = -6154439994035362196L;
  public static final org.apache.avro.Schema SCHEMA$ = new org.apache.avro.Schema.Parser().parse("{\"type\":\"record\",\"name\":\"Product\",\"namespace\":\"br.com.barroso.kafka.avroclient.avro\",\"fields\":[{\"name\":\"code\",\"type\":{\"type\":\"string\",\"avro.java.string\":\"String\"},\"doc\":\"Product code.\"},{\"name\":\"product_name\",\"type\":{\"type\":\"string\",\"avro.java.string\":\"String\"},\"doc\":\"Product name.\"},{\"name\":\"product_price\",\"type\":\"double\",\"doc\":\"Product price.\"},{\"name\":\"product_amount\",\"type\":\"int\",\"doc\":\"Product amount.\",\"default\":1}]}");
  public static org.apache.avro.Schema getClassSchema() { return SCHEMA$; }

  private static SpecificData MODEL$ = new SpecificData();

  private static final BinaryMessageEncoder<Product> ENCODER =
      new BinaryMessageEncoder<Product>(MODEL$, SCHEMA$);

  private static final BinaryMessageDecoder<Product> DECODER =
      new BinaryMessageDecoder<Product>(MODEL$, SCHEMA$);

  /**
   * Return the BinaryMessageDecoder instance used by this class.
   */
  public static BinaryMessageDecoder<Product> getDecoder() {
    return DECODER;
  }

  /**
   * Create a new BinaryMessageDecoder instance for this class that uses the specified {@link SchemaStore}.
   * @param resolver a {@link SchemaStore} used to find schemas by fingerprint
   */
  public static BinaryMessageDecoder<Product> createDecoder(SchemaStore resolver) {
    return new BinaryMessageDecoder<Product>(MODEL$, SCHEMA$, resolver);
  }

  /** Serializes this Product to a ByteBuffer. */
  public java.nio.ByteBuffer toByteBuffer() throws java.io.IOException {
    return ENCODER.encode(this);
  }

  /** Deserializes a Product from a ByteBuffer. */
  public static Product fromByteBuffer(
      java.nio.ByteBuffer b) throws java.io.IOException {
    return DECODER.decode(b);
  }

  /** Product code. */
   private java.lang.String code;
  /** Product name. */
   private java.lang.String product_name;
  /** Product price. */
   private double product_price;
  /** Product amount. */
   private int product_amount;

  /**
   * Default constructor.  Note that this does not initialize fields
   * to their default values from the schema.  If that is desired then
   * one should use <code>newBuilder()</code>.
   */
  public Product() {}

  /**
   * All-args constructor.
   * @param code Product code.
   * @param product_name Product name.
   * @param product_price Product price.
   * @param product_amount Product amount.
   */
  public Product(java.lang.String code, java.lang.String product_name, java.lang.Double product_price, java.lang.Integer product_amount) {
    this.code = code;
    this.product_name = product_name;
    this.product_price = product_price;
    this.product_amount = product_amount;
  }

  public org.apache.avro.Schema getSchema() { return SCHEMA$; }
  // Used by DatumWriter.  Applications should not call.
  public java.lang.Object get(int field$) {
    switch (field$) {
    case 0: return code;
    case 1: return product_name;
    case 2: return product_price;
    case 3: return product_amount;
    default: throw new org.apache.avro.AvroRuntimeException("Bad index");
    }
  }

  // Used by DatumReader.  Applications should not call.
  @SuppressWarnings(value="unchecked")
  public void put(int field$, java.lang.Object value$) {
    switch (field$) {
    case 0: code = (java.lang.String)value$; break;
    case 1: product_name = (java.lang.String)value$; break;
    case 2: product_price = (java.lang.Double)value$; break;
    case 3: product_amount = (java.lang.Integer)value$; break;
    default: throw new org.apache.avro.AvroRuntimeException("Bad index");
    }
  }

  /**
   * Gets the value of the 'code' field.
   * @return Product code.
   */
  public java.lang.String getCode() {
    return code;
  }


  /**
   * Gets the value of the 'product_name' field.
   * @return Product name.
   */
  public java.lang.String getProductName() {
    return product_name;
  }


  /**
   * Gets the value of the 'product_price' field.
   * @return Product price.
   */
  public java.lang.Double getProductPrice() {
    return product_price;
  }


  /**
   * Gets the value of the 'product_amount' field.
   * @return Product amount.
   */
  public java.lang.Integer getProductAmount() {
    return product_amount;
  }


  /**
   * Creates a new Product RecordBuilder.
   * @return A new Product RecordBuilder
   */
  public static br.com.barroso.kafka.avroclient.avro.Product.Builder newBuilder() {
    return new br.com.barroso.kafka.avroclient.avro.Product.Builder();
  }

  /**
   * Creates a new Product RecordBuilder by copying an existing Builder.
   * @param other The existing builder to copy.
   * @return A new Product RecordBuilder
   */
  public static br.com.barroso.kafka.avroclient.avro.Product.Builder newBuilder(br.com.barroso.kafka.avroclient.avro.Product.Builder other) {
    return new br.com.barroso.kafka.avroclient.avro.Product.Builder(other);
  }

  /**
   * Creates a new Product RecordBuilder by copying an existing Product instance.
   * @param other The existing instance to copy.
   * @return A new Product RecordBuilder
   */
  public static br.com.barroso.kafka.avroclient.avro.Product.Builder newBuilder(br.com.barroso.kafka.avroclient.avro.Product other) {
    return new br.com.barroso.kafka.avroclient.avro.Product.Builder(other);
  }

  /**
   * RecordBuilder for Product instances.
   */
  public static class Builder extends org.apache.avro.specific.SpecificRecordBuilderBase<Product>
    implements org.apache.avro.data.RecordBuilder<Product> {

    /** Product code. */
    private java.lang.String code;
    /** Product name. */
    private java.lang.String product_name;
    /** Product price. */
    private double product_price;
    /** Product amount. */
    private int product_amount;

    /** Creates a new Builder */
    private Builder() {
      super(SCHEMA$);
    }

    /**
     * Creates a Builder by copying an existing Builder.
     * @param other The existing Builder to copy.
     */
    private Builder(br.com.barroso.kafka.avroclient.avro.Product.Builder other) {
      super(other);
      if (isValidValue(fields()[0], other.code)) {
        this.code = data().deepCopy(fields()[0].schema(), other.code);
        fieldSetFlags()[0] = true;
      }
      if (isValidValue(fields()[1], other.product_name)) {
        this.product_name = data().deepCopy(fields()[1].schema(), other.product_name);
        fieldSetFlags()[1] = true;
      }
      if (isValidValue(fields()[2], other.product_price)) {
        this.product_price = data().deepCopy(fields()[2].schema(), other.product_price);
        fieldSetFlags()[2] = true;
      }
      if (isValidValue(fields()[3], other.product_amount)) {
        this.product_amount = data().deepCopy(fields()[3].schema(), other.product_amount);
        fieldSetFlags()[3] = true;
      }
    }

    /**
     * Creates a Builder by copying an existing Product instance
     * @param other The existing instance to copy.
     */
    private Builder(br.com.barroso.kafka.avroclient.avro.Product other) {
            super(SCHEMA$);
      if (isValidValue(fields()[0], other.code)) {
        this.code = data().deepCopy(fields()[0].schema(), other.code);
        fieldSetFlags()[0] = true;
      }
      if (isValidValue(fields()[1], other.product_name)) {
        this.product_name = data().deepCopy(fields()[1].schema(), other.product_name);
        fieldSetFlags()[1] = true;
      }
      if (isValidValue(fields()[2], other.product_price)) {
        this.product_price = data().deepCopy(fields()[2].schema(), other.product_price);
        fieldSetFlags()[2] = true;
      }
      if (isValidValue(fields()[3], other.product_amount)) {
        this.product_amount = data().deepCopy(fields()[3].schema(), other.product_amount);
        fieldSetFlags()[3] = true;
      }
    }

    /**
      * Gets the value of the 'code' field.
      * Product code.
      * @return The value.
      */
    public java.lang.String getCode() {
      return code;
    }

    /**
      * Sets the value of the 'code' field.
      * Product code.
      * @param value The value of 'code'.
      * @return This builder.
      */
    public br.com.barroso.kafka.avroclient.avro.Product.Builder setCode(java.lang.String value) {
      validate(fields()[0], value);
      this.code = value;
      fieldSetFlags()[0] = true;
      return this;
    }

    /**
      * Checks whether the 'code' field has been set.
      * Product code.
      * @return True if the 'code' field has been set, false otherwise.
      */
    public boolean hasCode() {
      return fieldSetFlags()[0];
    }


    /**
      * Clears the value of the 'code' field.
      * Product code.
      * @return This builder.
      */
    public br.com.barroso.kafka.avroclient.avro.Product.Builder clearCode() {
      code = null;
      fieldSetFlags()[0] = false;
      return this;
    }

    /**
      * Gets the value of the 'product_name' field.
      * Product name.
      * @return The value.
      */
    public java.lang.String getProductName() {
      return product_name;
    }

    /**
      * Sets the value of the 'product_name' field.
      * Product name.
      * @param value The value of 'product_name'.
      * @return This builder.
      */
    public br.com.barroso.kafka.avroclient.avro.Product.Builder setProductName(java.lang.String value) {
      validate(fields()[1], value);
      this.product_name = value;
      fieldSetFlags()[1] = true;
      return this;
    }

    /**
      * Checks whether the 'product_name' field has been set.
      * Product name.
      * @return True if the 'product_name' field has been set, false otherwise.
      */
    public boolean hasProductName() {
      return fieldSetFlags()[1];
    }


    /**
      * Clears the value of the 'product_name' field.
      * Product name.
      * @return This builder.
      */
    public br.com.barroso.kafka.avroclient.avro.Product.Builder clearProductName() {
      product_name = null;
      fieldSetFlags()[1] = false;
      return this;
    }

    /**
      * Gets the value of the 'product_price' field.
      * Product price.
      * @return The value.
      */
    public java.lang.Double getProductPrice() {
      return product_price;
    }

    /**
      * Sets the value of the 'product_price' field.
      * Product price.
      * @param value The value of 'product_price'.
      * @return This builder.
      */
    public br.com.barroso.kafka.avroclient.avro.Product.Builder setProductPrice(double value) {
      validate(fields()[2], value);
      this.product_price = value;
      fieldSetFlags()[2] = true;
      return this;
    }

    /**
      * Checks whether the 'product_price' field has been set.
      * Product price.
      * @return True if the 'product_price' field has been set, false otherwise.
      */
    public boolean hasProductPrice() {
      return fieldSetFlags()[2];
    }


    /**
      * Clears the value of the 'product_price' field.
      * Product price.
      * @return This builder.
      */
    public br.com.barroso.kafka.avroclient.avro.Product.Builder clearProductPrice() {
      fieldSetFlags()[2] = false;
      return this;
    }

    /**
      * Gets the value of the 'product_amount' field.
      * Product amount.
      * @return The value.
      */
    public java.lang.Integer getProductAmount() {
      return product_amount;
    }

    /**
      * Sets the value of the 'product_amount' field.
      * Product amount.
      * @param value The value of 'product_amount'.
      * @return This builder.
      */
    public br.com.barroso.kafka.avroclient.avro.Product.Builder setProductAmount(int value) {
      validate(fields()[3], value);
      this.product_amount = value;
      fieldSetFlags()[3] = true;
      return this;
    }

    /**
      * Checks whether the 'product_amount' field has been set.
      * Product amount.
      * @return True if the 'product_amount' field has been set, false otherwise.
      */
    public boolean hasProductAmount() {
      return fieldSetFlags()[3];
    }


    /**
      * Clears the value of the 'product_amount' field.
      * Product amount.
      * @return This builder.
      */
    public br.com.barroso.kafka.avroclient.avro.Product.Builder clearProductAmount() {
      fieldSetFlags()[3] = false;
      return this;
    }

    @Override
    @SuppressWarnings("unchecked")
    public Product build() {
      try {
        Product record = new Product();
        record.code = fieldSetFlags()[0] ? this.code : (java.lang.String) defaultValue(fields()[0]);
        record.product_name = fieldSetFlags()[1] ? this.product_name : (java.lang.String) defaultValue(fields()[1]);
        record.product_price = fieldSetFlags()[2] ? this.product_price : (java.lang.Double) defaultValue(fields()[2]);
        record.product_amount = fieldSetFlags()[3] ? this.product_amount : (java.lang.Integer) defaultValue(fields()[3]);
        return record;
      } catch (java.lang.Exception e) {
        throw new org.apache.avro.AvroRuntimeException(e);
      }
    }
  }

  @SuppressWarnings("unchecked")
  private static final org.apache.avro.io.DatumWriter<Product>
    WRITER$ = (org.apache.avro.io.DatumWriter<Product>)MODEL$.createDatumWriter(SCHEMA$);

  @Override public void writeExternal(java.io.ObjectOutput out)
    throws java.io.IOException {
    WRITER$.write(this, SpecificData.getEncoder(out));
  }

  @SuppressWarnings("unchecked")
  private static final org.apache.avro.io.DatumReader<Product>
    READER$ = (org.apache.avro.io.DatumReader<Product>)MODEL$.createDatumReader(SCHEMA$);

  @Override public void readExternal(java.io.ObjectInput in)
    throws java.io.IOException {
    READER$.read(this, SpecificData.getDecoder(in));
  }

}

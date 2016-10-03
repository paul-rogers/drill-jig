package org.apache.drill.jig.types;

import org.apache.drill.jig.api.FieldValue;
import org.apache.drill.jig.types.FieldAccessor.TypeAccessor;

/**
 * Field value container for a variant field. The container holds a cache
 * of field values so that the type-specific field values are reused across
 */

public class VariantFieldValueContainer implements FieldValueContainer {

  private final FieldValueCache valueCache;
  private TypeAccessor accessor;

  public VariantFieldValueContainer( FieldValueFactory factory ) {
    valueCache = new FieldValueCache( factory );
  }

  @Override
  public void bind(FieldAccessor accessor) {
    this.accessor = (TypeAccessor) accessor;
  }
  
  @Override
  public FieldValue get() {
    AbstractFieldValue value = valueCache.get( accessor.getType( ) );
    value.bind( accessor );
    return value;
  }
}

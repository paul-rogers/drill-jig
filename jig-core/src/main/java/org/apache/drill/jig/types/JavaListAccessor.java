package org.apache.drill.jig.types;

import java.util.List;

import org.apache.drill.jig.types.BoxedAccessor.VariantBoxedAccessor;
import org.apache.drill.jig.types.FieldAccessor.ArrayAccessor;

public class JavaListAccessor implements ArrayAccessor {
  
  public class JavaListMemberAccessor implements ObjectAccessor {

    @Override
    public boolean isNull() {
      return getObject( ) == null;
    }

    @Override
    public Object getObject() {
      return getList( ).get( index );
    }    
  }

  private final ObjectAccessor listAccessor;
  private final JavaListMemberAccessor memberAccessor = new JavaListMemberAccessor( );
  private int index;
  private final BoxedAccessor memberValueAccessor;

  public JavaListAccessor( ObjectAccessor listAccessor ) {
    this.listAccessor = listAccessor;
    memberValueAccessor = new BoxedAccessor( memberAccessor );
  }

  public JavaListAccessor( ObjectAccessor listAccessor, FieldValueFactory factory ) {
    this.listAccessor = listAccessor;
    memberValueAccessor = new VariantBoxedAccessor( memberAccessor, factory );
  }

  @Override
  public void bind(int index) {
    this.index = index;
  }
  
  @Override
  public int size() {
    if ( listAccessor.isNull() )
      return 0;
    return getList( ).size( );
  }

  @Override
  public Object getArray() {
    return listAccessor.getObject();
  }

  @Override
  public boolean isNull() {
    return listAccessor.isNull();
  }
  
  @SuppressWarnings("unchecked")
  private List<? extends Object> getList( ) {
    if ( listAccessor.isNull( ) )
      return null;
    return (List<? extends Object>) listAccessor.getObject( );
  }

  @Override
  public FieldAccessor memberAccessor() {
    return memberValueAccessor;
  }
  
//  public static class TypedJavaListAccessor extends JavaListAccessor {
//    
//    private final BoxedAccessor memberValueAccessor;
//    
//    public TypedJavaListAccessor( ObjectAccessor listAccessor ) {
//      super( listAccessor );
//      memberValueAccessor = new BoxedAccessor( memberAccessor );
//    }
//  }
//  
//  
//  public static class VariantJavaListAccessor extends JavaListAccessor {
//    
//    private final VariantBoxedAccessor memberValueAccessor;
//
//    public VariantJavaListAccessor( ObjectAccessor listAccessor, FieldValueFactory factory ) {
//      super( listAccessor );
//      memberValueAccessor = new VariantBoxedAccessor( memberAccessor, factory );
//    }
//  }
  
//  public JavaListAccessor( ObjectAccessor listAccessor, FieldValueFactory factory ) {
//    super( DataType.VARIANT );
//    this.listAccessor = listAccessor;
//    memberValueAccessor = new BoxedAccessor( memberAccessor );
//  }
  
//  @Override
//  public Object getObject() {
//    List<? extends Object> list = getList( );
//    if ( list == null )
//      return null;
//    if ( memberIndex < 0  ||  memberIndex <= list.size( ) )
//      return null;
//    return list.get( memberIndex );
//  }
//
//  @Override
//  public int size() {
//    List<? extends Object> list = getList( );
//    if ( list == null )
//      return 0;
//    return list.size( );
//  }
//
//  @Override
//  public void bind(int index) {
//    memberIndex = index;
//  }

}
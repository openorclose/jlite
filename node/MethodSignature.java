package node;

import common.Identifier;
import common.Type;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class MethodSignature {

    public List<Type> parameterTypes;
    public Identifier id;

    public MethodSignature(Identifier id, List<Type> parameterTypes) {
        this.id = id;
        this.parameterTypes = parameterTypes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        MethodSignature that = (MethodSignature) o;
        return parameterTypes.equals(that.parameterTypes) &&
                id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(parameterTypes, id);
    }

    @Override
    public String toString() {
        return id + "(" + parameterTypes.stream().map(Type::toString).collect(Collectors.joining(", ")) +")";
    }
}

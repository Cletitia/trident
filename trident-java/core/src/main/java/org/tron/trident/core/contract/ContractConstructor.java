package org.tron.trident.core.contract;

import org.bouncycastle.util.encoders.Hex;

import com.google.protobuf.ByteString;
import org.tron.trident.abi.datatypes.Type;
import org.tron.trident.abi.FunctionEncoder;
import org.tron.trident.abi.TypeEncoder;
import org.tron.trident.core.exceptions.ContractCreateException;
import org.tron.trident.proto.Common.SmartContract.ABI;
import org.tron.trident.proto.Common.SmartContract.ABI.Entry;
import org.tron.trident.proto.Common.SmartContract.ABI.Entry.Param;

import java.lang.StringBuilder;
import java.util.ArrayList;
import java.util.List;

public class ContractConstructor {

    private Entry rawConstructor;

    private List paramTypes;

    private boolean payable;

    private String bytecode = "";
    
    public ContractConstructor(Entry raw) {
        this.rawConstructor = raw;
        this.paramTypes = new ArrayList<String>();
        
        for (Param p : raw.getInputsList()) {
            paramTypes.add(p.getType());
        }

        this.payable = raw.getPayable();
    }

    public Entry getRawConstructor() {
        return this.rawConstructor;
    }

    public List getParamTypes() {
        return this.paramTypes;
    }

    public boolean getPayable() {
        return this.payable;
    }

    public String getBytecode() {
        return this.bytecode;
    }

    public void encodeParameter(List<Type> params) throws ContractCreateException {
        if (params.size() != paramTypes.size()) {
            throw new ContractCreateException("Parameter amount doesn't match.");
        }
        this.bytecode = FunctionEncoder.encodeConstructor(params);
    }
}

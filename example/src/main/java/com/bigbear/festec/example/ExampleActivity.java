package com.bigbear.festec.example;

import com.bigbear.bigbear_core.activities.ProxyActivity;
import com.bigbear.bigbear_core.delegates.BigBearDeleate;

public class ExampleActivity extends ProxyActivity {

    @Override
    public BigBearDeleate setRootDelegate() {
        return new ExampleDelegate();
    }

}

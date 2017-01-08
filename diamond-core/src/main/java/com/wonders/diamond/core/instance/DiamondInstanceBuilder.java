package com.wonders.diamond.core.instance;

import com.google.common.collect.Lists;
import com.wonders.diamond.core.utils.LocalIpFilter;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Collection;
import java.util.Enumeration;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Created by ningyang on 2017/1/2.
 * @author ningyang
 */
public class DiamondInstanceBuilder {

    private static DiamondInstance instance;

    private static final AtomicReference<LocalIpFilter> localIpFilter = new AtomicReference<>
            (
                    (nif, adr) -> (adr != null) && !adr.isLoopbackAddress() && (nif.isPointToPoint() || !adr.isLinkLocalAddress())
            );

    public DiamondInstanceBuilder builder() throws SocketException {
        instance = new DiamondInstance();
        String                  address = null;
        Collection<InetAddress> ips = DiamondInstanceBuilder.getAllLocalIPs();
        if ( ips.size() > 0 )
        {
            address = ips.iterator().next().getHostAddress();   // default to the first address
        }

        return this.address(address);
    }

    public DiamondInstanceBuilder address(String address){
        instance.setAddress(address);
        return this;
    }

    public DiamondInstanceBuilder port(Integer port){
        instance.setPort(port);
        return this;
    }

    public DiamondInstanceBuilder id(String id){
        instance.setId(id);
        return this;
    }

    public DiamondInstanceBuilder name(String name){
        instance.setName(name);
        return this;
    }

    public DiamondInstanceBuilder type(String type){
        instance.setType(type);
        return this;
    }

    public DiamondInstanceBuilder registerTime(long registerTime){
        instance.setRegisterTime(registerTime);
        return this;
    }

    public DiamondInstance build(){
        return instance;
    }

    public static Collection<InetAddress> getAllLocalIPs() throws SocketException
    {
        List<InetAddress> listAdr = Lists.newArrayList();
        Enumeration<NetworkInterface> nifs = NetworkInterface.getNetworkInterfaces();
        if (nifs == null) return listAdr;

        while (nifs.hasMoreElements())
        {
            NetworkInterface nif = nifs.nextElement();
            // We ignore subinterfaces - as not yet needed.

            Enumeration<InetAddress> adrs = nif.getInetAddresses();
            while ( adrs.hasMoreElements() )
            {
                InetAddress adr = adrs.nextElement();
                if ( localIpFilter.get().use(nif, adr) )
                {
                    listAdr.add(adr);
                }
            }
        }
        return listAdr;
    }
}


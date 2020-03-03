package iii.aihub.utils;

import java.io.InputStream;
import java.util.Collection;
import java.util.Collections;
import java.util.Properties;

import org.springframework.core.io.Resource;

import com.google.common.io.Closeables;

import javax.annotation.*;

/**
 * 
 * @author Lee
 * {@https://stackoverflow.com/questions/4116681/how-to-load-system-properties-file-in-spring}
 */
public class SystemPropertiesReader {

	public SystemPropertiesReader() {
	}

	private Collection<Resource> resources;

    public void setResources(final Collection<Resource> resources){
        this.resources = resources;
    }

    public void setResource(final Resource resource){
        resources = Collections.singleton(resource);
    }

    //-- TODO 待補完

    //@PostConstruct
    public void applyProperties() throws Exception{
        final Properties systemProperties = System.getProperties();
        for(final Resource resource : resources){
            final InputStream inputStream = resource.getInputStream();
            try{
                systemProperties.load(inputStream);
            } finally{
                // Guava
                Closeables.closeQuietly(inputStream);
            }
        }
    }
	
}

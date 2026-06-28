package by.dheeraj.musicadvisor.context.singleton;

public record SingletonDefinition(String name,
                                  Class<?> singletonClass,
                                  SingletonCtor singletonCtor,
                                  SingletonMethod singletonMethod) {
}

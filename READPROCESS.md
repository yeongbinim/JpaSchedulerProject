# 고민 과정

[BCrypt 모듈 선택](#BCrypt-모듈-선택)
[JPA 제네릭 타입에 ID 타입 넘기는 이유](#JPA-제네릭-타입에-ID-타입-넘기는-이유)

## BCrypt 모듈 선택

bcrypt라는 단방향 알고리즘으로 PasswordEncoder 구현체를 작성하려 했는데, 내가 이 알고리즘을 직접 구현하는건 최악이고.. ㅎㅎ 알아보니 자주 사용되는 것으로
`org.mindrot.jbcrypt` 와 `at.favre.lib.crypto.bcrypt` 가 있었다.

순전한 호기심에 둘 다 사용해 보기로 했다.

<br/>

**[org.mindrot.jbcrypt 사용하기]**

```java
implementation 'org.mindrot:jbcrypt:0.4'
```

```java
import org.mindrot.jbcrypt.BCrypt;

String rawPassword = "1234";
String encodedPassword = BCrypt.hashpw(rawPassword, BCrypt.gensalt(12)); //해싱
boolean verified = BCrypt.checkpw(rawPassword, encodedPassword); //검증
```

굉장히 간단했다. 해싱하기 위해서 `Bcrypt.hashpw()`를 사용하고, 검증하기 위해서 `Bcrypt.checkpw()` 를 사용했다.

이때 레인보우 테이블 공격을 막기 위해서 `BCrypt.gensalt(WORK_FACTOR)`로 패스워드 해싱할 때 사용할 솔트를 생성하게끔 한다.

<br/>

**[at.favre.lib.crypto.bcrypt 사용하기]**

```java
implementation 'at.favre.lib:bcrypt:0.10.2'
```

```java
import at.favre.lib.crypto.bcrypt.BCrypt;

String rawPassword = "1234";
String encodedPassword = BCrypt.withDefaults()
	.hashToString(12, rawPassword.toCharArray()); //해싱

BCrypt.Result result = BCrypt.verifyer()
	.verify(rawPassword.toCharArray(), encodedPassword); //검증
boolean verified = result.verified;
```

뭔가 더 객체지향 적인 느낌이 들었다.

팩터리 메서드들 (`withDefaults()`, `verifier()`)을 통해 해싱 생성기 객체(`BCrypt.Hasher`)를 생성하거나, 검증기 객체(
`BCrypt.Verifyer`)를 생성하였고

그 객체로부터 메서드들을 호출하는 방식이었다.

왜 이렇게 확장성을 고려했나.. 궁금해서 알아봤더니 Bcrypt도 알고리즘 버전이 나뉘어져 있었다. (그리고 해시를 생성할 때 버전 식별자를 솔트의 일부로 포함시켜서, 생성된
해시가 어떤 버전의 BCrypt 알고리즘을 사용했는지도 알 수 있다고 한다.)

기본으로 최신 버전에서는 2014년에 출시된 `$2b$` BCrypt를 사용하게 된다.

그런데 `$2a$` 를 사용하고 싶다? 하면 아래처럼 하면 된다.

```java
String rawPassword = "1234";

BCrypt.Hasher hasher = BCrypt.with(BCrypt.Version.VERSION_2A);
BCrypt.Verifyer verifyer = BCrypt.verifyer(BCrypt.Version.VERSION_2A);
```

<br/>

**[결론]**

`at.favre.lib.crypto.bcrypt`가 더 관리되고 있는 느낌을 받아서 이걸 사용하기로 했다.

기본 패스워드 해싱과 검증만 필요하고 사용의 단순함만을 고려했을 때에는 `org.mindrot.jbcrypt` 가 더 나아보였지만 이건 아직도 `$2a$` 버전의 BCrypt
알고리즘을 사용한다고 한다.

## JPA 제네릭 타입에 ID 타입 넘기는 이유

```java
public interface JpaMemberRepository extends JpaRepository<MemberEntity, Long> {

}
```

이렇게 인터페이스를 정의하면, Spring Data JPA가 실행 시 이 인터페이스의 구현체를 자동으로 생성하는데, 이 과정에서 리플렉션과 프록시를 사용한다고 한다.

리플렉션을 사용할 건데 왜 굳이 id값이 Long 타입이라고 정보를 넘기는 걸까?

이미 MemberEntity를 기준으로 할거라고 테이블 정보를 넘겼고, 여기에서 리플렉션을 통해 `@Id` 가 있는 필드를 찾을게 아닌가?

<br/>

알아보니 두번째 넘기는 Id는 리플렉션에 활용되기 위한 용도가 아니라, 타입정보를 제공받기 위해서였다.

<img width="400" src="https://github.com/user-attachments/assets/bf566003-3ccf-4873-af86-6c8fe7315e82" />

위와 같이 개발단계에서 findById 와같은 메서드의 타입 정보를 제공받을 수 있으며, 만약 MemberEntity만으로 넘긴다고 할 시, 리플렉션은 런타임에 일어나기 때문에 저
타입정보를 제공받을 수 없는 것이다.

아 물론 첫번째 인자인 MemberEntity는 타입 정보 제공 뿐만 아니라, Spring Data JPA 내부적으로 리플렉션을 통한 여러 작업에 사용되며, 이걸 통해 엔티티에
대한 다양한 메타데이터를 자동으로 추출하고 관리할 수 있다.

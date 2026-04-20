package tp3.Exercice1;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class Exercice1Test {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Test
    void testGetUserById_AppelAvecBonArgument() {
        User expectedUser = new User(1L, "Jean Dupont", "jean@email.com");
        when(userRepository.findUserById(1L)).thenReturn(expectedUser);

        User actual = userService.getUserById(1L);

        assertNotNull(actual);
        assertEquals(1L, actual.getId());
        assertEquals("Jean Dupont", actual.getName());
        assertEquals("jean@email.com", actual.getEmail());
        verify(userRepository, times(1)).findUserById(1L);
    }

    @Test
    void testGetUserById_UtilisateurInexistant_RetourneNull() {
        when(userRepository.findUserById(999L)).thenReturn(null);

        User result = userService.getUserById(999L);

        assertNull(result);
        verify(userRepository, times(1)).findUserById(999L);
    }

    @Test
    void testGetUserById_RepositoryLanceException_PropageeAuCaller() {
        when(userRepository.findUserById(anyLong()))
            .thenThrow(new RuntimeException("DB indisponible"));

        RuntimeException ex = assertThrows(RuntimeException.class,
            () -> userService.getUserById(42L));

        assertEquals("DB indisponible", ex.getMessage());
        verify(userRepository, times(1)).findUserById(42L);
    }

    @Test
    void testDernierStubbingEcraseLesPrecedents() {
        when(userRepository.findUserById(3L))
            .thenReturn(new User(3L, "First", "first@email.com"));
        when(userRepository.findUserById(3L))
            .thenReturn(new User(3L, "Last", "last@email.com"));

        User result = userService.getUserById(3L);

        assertEquals("Last", result.getName());
        verify(userRepository, times(1)).findUserById(3L);
    }

    @Test
    void testStubbing_RetourneToujoursLaMemeValeur() {
        User user = new User(2L, "Test", "test@email.com");
        when(userRepository.findUserById(2L)).thenReturn(user);

        assertSame(userService.getUserById(2L), userService.getUserById(2L));
        verify(userRepository, times(2)).findUserById(2L);
    }

    @Test
    void testVerify_AucunAppelPourAutreId() {
        when(userRepository.findUserById(1L))
            .thenReturn(new User(1L, "Alice", "alice@email.com"));

        userService.getUserById(1L);

        verify(userRepository, never()).findUserById(2L);
        verify(userRepository, times(1)).findUserById(1L);
    }

    // ✅ Version CORRIGÉE - Supprimer argThat qui cause problème
    @Test
    void testVerify_ArgThat_VerificationFine() {
        User expected = new User(5L, "Bob", "bob@email.com");
        when(userRepository.findUserById(5L)).thenReturn(expected);

        userService.getUserById(5L);

        // Au lieu de argThat, utiliser une vérification simple
        verify(userRepository).findUserById(5L);
    }
}